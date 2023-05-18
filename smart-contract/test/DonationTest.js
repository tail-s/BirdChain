var Donation = (Donation = artifacts.require("./Donation.sol"));

contract("Donation", function (accounts) {
  var donationInstance;

  
  it("컨트랙 자체 잔액 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.getContractBalance();
      })
      .then(function (balance) {
        console.log(balance);
      });
  });

  it("잔고가 잘 조회되는지 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.checkValueNow();
      })
      .then(function (balance) {
        console.log(balance);
      });
  });

  it("emit이 제대로 작동하는지 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.donate({
          from: accounts[0],
          value: web3.utils.toWei("1", "ether"),
        });
      })
      .then(function (receipt) {
        console.log(receipt.logs[0].args.sender);
      })
      .catch((err) => {
        console.log(err);
      });
  });

  it("emit이 제대로 작동하는지 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.donate({
          from: accounts[0],
          value: web3.utils.toWei("0.01", "ether"),
        });
      })
      .then(function (receipt) {
        console.log(receipt.logs[0].args.sender);
      });
  });

  it("콘트랙트에 송금하기", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.sendEtherToContract(instance.address, {
          from: accounts[0],
          value: web3.utils.toWei("10", "ether"),
        });
      })
      .then(function (receipt) {
        console.log(receipt);
      });
  });

  it("totalContribution 값이 잘 변경되는지 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.getTotalContribution();
      })
      .then(function (total) {
        console.log(total);
      });
  });

  it("잔고가 잘 조회되는지 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.checkValueNow();
      })
      .then(function (balance) {
        console.log(balance);
      });
  });

  it("컨트랙 자체 잔액 확인", function () {
    return Donation.deployed()
      .then(function (instance) {
        donationInstance = instance;
        return donationInstance.getContractBalance();
      })
      .then(function (balance) {
        console.log(balance);
      });
  });

});
