import { format } from "path";

export default 
function time(unixTimestamp){

    let date = new Date(unixTimestamp);
    // Hours part from the timestamp
    let hours = date.getHours();
    let militaryHours = date.getHours();
    // Minutes part from the timestamp
    let minutes = "0" + date.getMinutes();
    // Seconds part from the timestamp
    let seconds = "0" + date.getSeconds();
    let year =  date.getFullYear();
    let month = date.getMonth();
    let day =  date.getDate();

    if (hours > 12){
        hours = hours - 12
    }
    // Will display time in 10:30:23 format
    let formattedTime = `${hours}:${minutes.substr(-2)}:${seconds.substr(-2)}`;

    let formatDate = `${month+1}/${day}/${year}`
    let formattedDateAM = `${formatDate} - ${formattedTime} AM`
    let formattedDatePM = `${formatDate} - ${formattedTime} PM`
    
    if(unixTimestamp > 0 && militaryHours < 12){
        return formattedDateAM;
    }
    else if(unixTimestamp > 0 && militaryHours >= 12){
        return formattedDatePM
    } else {
        return ''
    }

}