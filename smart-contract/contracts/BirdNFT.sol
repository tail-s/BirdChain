// SPDX-License-Identifier: MIT
pragma solidity >=0.4.22 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721URIStorage.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract BirdNFT is ERC721URIStorage {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;
    
    event EtherReceived(address sender, uint256 amount);
    event NFT(address sender, uint256 tokenId);

    mapping(uint256 => string) private _metaDatas;

    constructor() ERC721("BirdNFT", "BNFT") {}

    //토큰 등록
    function createNFT(string memory imgURI, string memory metaData) public payable returns (uint256) {
        _tokenIds.increment();
        uint256 newItemId = _tokenIds.current();
        _mint(msg.sender, newItemId);
        _setTokenURI(newItemId, imgURI);
        _setMetaDatas(newItemId, metaData);
        //가스비 환불
        payable(msg.sender).transfer(calculateGasFee());
        emit NFT(msg.sender, newItemId);
        return newItemId;
    }

    function _setMetaDatas(uint256 newItemId, string memory metaData) internal {
        _metaDatas[newItemId] = metaData;
    }

    function mataData(uint256 newItemId) public view returns (string memory) {
        require(_exists(newItemId), "BirdNFT: URI query for nonexistent token");
        string memory uri = _metaDatas[newItemId];
        return uri;
    }

    function calculateGasFee() internal view returns (uint256) {
        // 가스비 계산 로직을 구현합니다.
        // 예시로 고정 가스비를 0.001 ether로 설정하였습니다.
        uint256 gasPrice = tx.gasprice;
        uint256 gasLimit = gasleft();
        uint256 gasFee = gasPrice * gasLimit;
        
        // 최소한의 가스비로 설정하기 위해 가스비를 조정합니다.
        gasFee = gasFee < 0.001 ether ? gasFee : 0.001 ether;
        
        return gasFee;
    }

    //토큰 URI 조회하기
    function tokenURI(uint256 tokenId) public view virtual override(ERC721URIStorage) returns (string memory) {
        return super.tokenURI(tokenId);
    }

    //토큰 지우기
    function deleteNFT(uint256 tokenId) public {
        _burn(tokenId);
    }

      //Contract 자체 이더 조회
     function getContractBalance() public view returns (uint256) {
        return address(this).balance;
    }

    //콘트렉트 자체에 이더 송금하면 자동으로 발동하는 함수 여러가지를 처리할 수 있음
    receive() external payable {
        emit EtherReceived(msg.sender, msg.value);
    }

     //콘트렉트 자체에 이더 송금
     function sendEtherToContract(address payable contractAddress) external payable {
        contractAddress.transfer(msg.value);
    }
}
