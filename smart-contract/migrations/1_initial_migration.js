const Migrations = artifacts.require("./Migrations.sol");
const Donation = artifacts.require("./Donation.sol");
const BirdNFT = artifacts.require("./BirdNFT.sol");

module.exports = function (deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(Donation); 
  deployer.deploy(BirdNFT); 

    // deployer.deploy(Donation); 
  // deployer.deploy(SsafyToken, "SSAFY", "SSF", 0);
};
