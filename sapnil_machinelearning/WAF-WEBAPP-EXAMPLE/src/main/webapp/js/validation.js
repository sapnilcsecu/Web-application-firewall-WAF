/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function phonenumber(inputtxt)
{
    var phoneno = /^\d{11}$/;
    if (inputtxt.match(phoneno)) {
        return true;
    } else
    {
        alert("invalid mobile no");
        return false;
    }
}


