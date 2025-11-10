// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract Bank {
    
    uint balance;
    address public acOwner;

    constructor(){
        acOwner = msg.sender;
    }
    
    function deposite(uint amt) public {
        require(acOwner == msg.sender,"You are not an account owner");
        require(amt > 0, "Deposite amount should be greater than 0");

        balance+=amt;
    }

    function withDraw(uint amt) public {
        require(acOwner == msg.sender,"You are not an account owner");
        require(balance > 0, "Balance amount should be greater than 0");

        balance-=amt;
    }

    function showBalance() public view returns(uint){
        require(acOwner == msg.sender,"You are not an account owner");
        return balance;
    }
}
