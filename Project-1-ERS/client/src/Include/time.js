
// Create a new JavaScript Date object based on the timestamp
// multiplied by 1000 so that the argument is in milliseconds, not seconds.

export default 
function time(unix_timestamp){

    let date = new Date(unix_timestamp);
    // Hours part from the timestamp
    let hours = date.getHours();
    // Minutes part from the timestamp
    let minutes = "0" + date.getMinutes();
    // Seconds part from the timestamp
    let seconds = "0" + date.getSeconds();
    let year =  date.getFullYear();
    let month = date.getMonth();
    let day =  date.getDate();

    // Will display time in 10:30:23 format
    let formattedTime = `${hours}:${minutes.substr(-2)}:${seconds.substr(-2)}`;
    let formatDate = `${year}-${month+1}-${day}`
    let formattedDate = `${formatDate} - ${formattedTime}`
    if(unix_timestamp > 0){
        return formattedDate;
    } else {
        return 'TBD'
    }
    
}


