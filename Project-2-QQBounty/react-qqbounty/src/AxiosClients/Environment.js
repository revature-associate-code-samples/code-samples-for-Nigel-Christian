
const dev = {
  qqBountyContext: process.env.REACT_APP_SERVER_ADDRESS,
  lndContext: process.env.LND_SERVER_ADDRESS


};
  
const prod = {
  qqBountyContext: "Qqbounty-env.iegq3taxdj.us-west-2.elasticbeanstalk.com",
  lndContext: process.env.LND_SERVER_ADDRESS
};

export const environment = process.env.NODE_ENV === "production" ? prod : dev;
