/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getcurrentdate() {

    var today = new Date();
    var dd = today.getDate();

    var mm = today.getMonth() + 1;
    var yyyy = today.getFullYear();
    if (dd < 10)
    {
        dd = '0' + dd;
    }

    if (mm < 10)
    {
        mm = '0' + mm;
    }
    
    today = dd+ '/' + mm + '/' + yyyy;
    console.log(today);
    

    return today;
}
