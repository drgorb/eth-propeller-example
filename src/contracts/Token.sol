pragma solidity ^0.4.8;

contract Token {
    uint public supply;
    address public owner;
    
    mapping(address => uint) public balances;
    uint public price;
    
    function Token(uint _supply, uint _priceWeiPerToken) {
        supply = _supply;
        price = _priceWeiPerToken;
        owner = msg.sender;
        balances[owner] = _supply / 10;
    }
    
    function buy() payable returns (bool) {
        uint tokensToTransfer = msg.value / price;
        if (tokensToTransfer > supply)
            throw;
        balances[msg.sender] = tokensToTransfer;
        supply -= tokensToTransfer;
        return true;
    }
    
    function transfer(address _to, uint _amount) returns (bool) {
        if (balances[msg.sender] < _amount)
            return false;
        balances[msg.sender] -= _amount;
        balances[_to] += _amount;
        return true;
    }
    
    function payOut() returns (bool) {
        if (msg.sender != owner)
            throw;
        bool res = msg.sender.send(this.balance);
        return res;
    }
}
