var BirdNFT = BirdNFT = artifacts.require("./BirdNFT.sol");

contract("BirdNFT", function (accounts) {
  var birdNFTInstance;
  const imageURI = 'https://www.dropbox.com/s/6q1pr8byih5yjpk/SSAFY%20%EB%A1%9C%EA%B3%A0.PNG?dl=0';
  const metaData = 'https://www.dropbox.com/s/6q1pr8byih5yjpk/SSAFY%20%EB%A1%9C%EA%B3%A0.PNG?dl=0';

  it("콘트랙트에 송금하기", function () {
    return BirdNFT.deployed()
      .then(function (instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.sendEtherToContract(instance.address, {
          from: accounts[0],
          value: web3.utils.toWei("10", "ether"),
        });
      })
      .then(function (receipt) {
        console.log(receipt);
      });
  });

  it("NFT가 잘 등록되었는지 확인", function () {
    return BirdNFT.deployed()
      .then(function(instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.createNFT(accounts[9], imageURI, metaData);
      })
      .then(function (receipt) {
        // console.log(newItemId);
        console.log(receipt);
        console.log(receipt.tx);
        console.log(receipt.receipt.blockNumber);
        console.log(receipt.receipt.logs[0].args.tokenId.words[0]); //토큰 아이디
      });
  });

  it("컨트랙 자체 잔액 확인", function () {
    return BirdNFT.deployed()
      .then(function (instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.getContractBalance();
      })
      .then(function (balance) {
        console.log(balance);
      });
  });

  it("토큰으로 URI 조회하기", function () {
    return BirdNFT.deployed()
      .then(function(instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.tokenURI(1);
      })
      .then(function (uri) {
        console.log(uri);
      });
  });

  it("토큰 지우기", function(){
    return BirdNFT.deployed()
      .then(function(instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.deleteNFT(1);
      });
  })

  it("토큰으로 지운 NFT URI 조회하기", function () {
    return BirdNFT.deployed()
      .then(function(instance) {
        birdNFTInstance = instance;
        return birdNFTInstance.tokenURI(1);
      })
      .then(function (uri) {
        console.log(uri);
      }).catch(err=> {
        console.log(err);
      });
  });

});