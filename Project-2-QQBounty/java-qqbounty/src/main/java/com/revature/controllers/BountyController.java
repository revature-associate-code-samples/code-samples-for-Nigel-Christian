package com.revature.controllers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.JwtUserIsAdmin;
import com.revature.annotations.JwtUserOwnBounty;
import com.revature.annotations.JwtVerify;
import com.revature.dto.AnswerDto;
import com.revature.dto.BountyInputDto;
import com.revature.models.Answer;
import com.revature.models.Bounty;
import com.revature.models.Subject;
import com.revature.models.User;
import com.revature.models.Wallet;
import com.revature.services.AnswerService;
import com.revature.services.BountyService;
import com.revature.services.UserService;
import com.revature.services.WalletService;
import com.revature.util.JwtUtil;
import com.revature.util.ResponseMap;

@RestController
@RequestMapping(path = "bounties")
public class BountyController {

	
	@Autowired
	private BountyService bs;

	@Autowired
	private AnswerService as;

	@Autowired
	private UserService us;

	@Autowired
	private WalletService ws;

	@Autowired
	private JwtUtil sJwtUtil;
	
	// check for logged in user here
	// will pass user id from extracted jwt here
	// add subjects after creating bounty
	@ResponseBody
	@PostMapping
	@JwtVerify
	public ResponseEntity<Map<String, Object>> save(@RequestBody BountyInputDto bountyInput, HttpServletRequest req) {
		System.out.println(bountyInput);
		
		JwtUtil j = new JwtUtil();
		int userId = j.extractUserId(req);
		
		System.out.println("User Id: " + userId);
		String userData = us.findById(userId).toString();
		
		// Subtract Balance
		Pattern uPattern = Pattern.compile("walletId=(.*?),");
		Matcher uMatcher = uPattern.matcher(userData);
		uMatcher.find();
		int xWalletId = Integer.parseInt(uMatcher.group(1));

		// find wallet by id
		Wallet wallet = ws.getOne(xWalletId);
		int walletBalance = wallet.getBalance();

		if (walletBalance - bountyInput.getAmount() < 0) {
			System.out.println("Not enough money in in wallet :(");
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Not enough money in in wallet :("));
		}
		
		Set<Subject> subjectSet = new HashSet<>();
		String[] subjects = bountyInput.getSubjects();
		if (subjects.length != 0){
			for (int i = 0; i < subjects.length;i++) {
				Subject subject = bs.getOne(subjects[i]);
				subjectSet.add(subject);
			}
		}
		Bounty pBounty = new Bounty(bountyInput,userId,subjectSet);
		
		
		wallet.setBalance(wallet.getBalance() - bountyInput.getAmount());
		ws.update(wallet);

		Map<String, Object> tResult = (Map<String, Object>) bs.save(pBounty);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}

		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	// check that registered user is logged in here
	@GetMapping
	public ResponseEntity<Map<String, Object>> findAll(Pageable pageable) {
		Map<String, Object> tResult = (Map<String, Object>) bs.findAll(pageable);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	@GetMapping("subject")
	public ResponseEntity<Map<String, Object>> findAllBySubjectTags(Pageable pageable,
			@RequestParam(value = "subjects", required = true) String[] subjects) {
		System.out.println(pageable);
		List<String> subjectsList = Arrays.asList(subjects);

		System.out.println(subjectsList);

		Map<String, Object> tResult = (Map<String, Object>) bs.findAllBySubjectTag(pageable, subjectsList);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	@GetMapping("popular")
	public ResponseEntity<Map<String, Object>> findAllByOrderByVotes(Pageable pageable) {
		Map<String, Object> tResult = (Map<String, Object>) bs.findAllByOrderByVotes(pageable);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	@GetMapping("cost")
	public ResponseEntity<Map<String, Object>> findAllByOrderByAmount(Pageable pageable) {
		Map<String, Object> tResult = (Map<String, Object>) bs.findAllByOrderByAmount(pageable);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));

	}

	@GetMapping("newest")
	public ResponseEntity<Map<String, Object>> findAllByOrderByNewest(Pageable pageable) {
		Map<String, Object> tResult = (Map<String, Object>) bs.findAllByOrderByNewest(pageable);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}
	
	@GetMapping("oldest")
	public ResponseEntity<Map<String, Object>> findAllByOrderByOldest(Pageable pageable) {
		Map<String, Object> tResult = (Map<String, Object>) bs.findAllByOrderByOldest(pageable);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

// 	public ResponseEntity<Map<String, Object>> findAll(HttpServletRequest req) throws IOException {
// 		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(bs.findAll()));
//   }

	// check for logged in as admin for this
	@PatchMapping
	@JwtUserIsAdmin
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Bounty bounty) {
		Map<String, Object> tResult = (Map<String, Object>) bs.save(bounty);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	// check for registered user is logged in here
	// get answers by bounty id
	@GetMapping("{id}/answers")
	public ResponseEntity<Map<String, Object>> findByBountyid(@PathVariable int id, Pageable page) {
		Map<String, Object> tResult = (Map<String, Object>) as.findByBountyId(id, page);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	// check for registered user is logged in here
	// get correct answer
	@PatchMapping("{bountyId}/answers/{answerId}")
	@JwtUserOwnBounty
	public ResponseEntity<Map<String, Object>> chooseBestAnswer(@PathVariable int bountyId, @PathVariable int answerId,
			HttpServletRequest req) {

		Bounty bounty = bs.findById(bountyId);
		Answer answer = new Answer((AnswerDto) as.findById(answerId).get("answers"));

		if (bounty.getStatusId() != 1) {
			System.out.println("Bounty already answered or expired.");
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Bounty already answered or expired."));
		}
		if (bounty.getUserId() == answer.getAnswerId()) {
			System.out.println("Bounty User is same Answer User");
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Bounty User is same as Answer User"));
		}
		bounty.setCorrectAnswerId(answerId); // Set correct answer to answer id.
		bounty.setStatusId(2); // Answer
		answer.setStatusId(3); // Best Answer
		System.out.println(bounty);
		as.update(answer);
		Map<String, Object> tResult = (Map<String, Object>) bs.update(bounty);
		if (tResult == null) {
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
		}


		updateWallet(answer.getUserId(),bounty.getAmount());
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
	}

	@PatchMapping("answers")
	public ResponseEntity<Map<String, Object>> chooseBestAnswer(List<Integer> bountyList) {
		System.out.println(bountyList);
		for (int i = 0; i < bountyList.size(); i++) {
			checkVotesAutomatically(bountyList.get(i));
		}

		System.out.println("here");
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse("Updated Votes"));
	}

	@JwtVerify
	@PatchMapping("{id}/vote")
	public ResponseEntity<Map<String, Object>> updateVote(@PathVariable int id, @RequestParam(value = "voteValue", required = true) int voteValue, HttpServletRequest req) throws IOException {
		int userId = sJwtUtil.extractUserId(req);
		
		Map<String, Object> bResult = (Map<String, Object>) bs.updateVote(id,userId,voteValue);
		
		System.out.println("Vote request is " + (boolean) bResult.get("vote_status") );
		
		
		if((boolean) bResult.get("vote_status") == false) {
			System.out.println("Vote did not go through");
			return ResponseEntity.badRequest().body(ResponseMap.getBadResponse("Already Voted"));
		}
		return ResponseEntity.ok().body(ResponseMap.getGoodResponse(bResult));
	}
	
	public void checkVotesAutomatically(int bountyId) {
		Bounty bounty = bs.getBounty(bountyId);
		@SuppressWarnings("unchecked")
		List<AnswerDto> tempList = (List<AnswerDto>) as.findByBountyId(bountyId).get("answers");
		System.out.println(tempList.size());
		if (tempList.size() == 0) {
			bounty.setStatusId(3);
			int id = bounty.getUserId();
			bs.update(bounty);
			updateWallet(id, bounty.getAmount());
		}else {
			List<Answer> answers = as.getHighestAnswers(bountyId);
			if (answers.size() > 1) {
				int numOfAnswer = answers.size();
				int bountyRewardSplit = bounty.getAmount() / numOfAnswer;
				bounty.setStatusId(2);
				answers.forEach(answer -> {
					answer.setStatusId(3);
					as.update(answer);
					int id = answer.getUserId();
					updateWallet(id, bountyRewardSplit);
				});
				bs.update(bounty);
			} else if (answers.size() == 1) {
				Answer answer = answers.get(0);
				bounty.setCorrectAnswerId(answer.getAnswerId()); // Set correct answer to answer id.
				bounty.setStatusId(2);
				answer.setStatusId(3);
				System.out.println("WEHRERERERERE");
				as.update(answer);
				bs.update(bounty);
				int id = answer.getUserId();
				updateWallet(id, bounty.getAmount());
			} else {
				bounty.setStatusId(3);
				System.out.println("DFDSFDS");
				int id = bounty.getUserId();
				bs.update(bounty);
				updateWallet(id, bounty.getAmount());
			}
		}
	}

	private void updateWallet(int id, int amount) {
		User userData = us.findUser(id);
		// extract balance
		
		// find wallet by id
		Wallet walletData = ws.findWallet(userData.getWalletId());
		walletData.setBalance(walletData.getBalance() + amount);
		ws.update(walletData);
	}
	
	// check that registered user is logged in here
		@GetMapping("user")
		@JwtVerify
		public ResponseEntity<Map<String, Object>> findUserBounties(Pageable pageable, HttpServletRequest req) {
			//extract user id
			JwtUtil j = new JwtUtil();
			int id = j.extractUserId(req);
			
			Map<String, Object> tResult = (Map<String, Object>) bs.findUserBounties(pageable, id);
			if (tResult == null) {
				return ResponseEntity.badRequest().body(ResponseMap.getBadResponse());
			}
			return ResponseEntity.ok().body(ResponseMap.getGoodResponse(tResult));
		}
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		
		public void trial() {
			System.out.println("hi");
		}

		public void checkBounties() {
			System.out.println("here");
			chooseBestAnswer(bs.checkBounties());
		}

}
