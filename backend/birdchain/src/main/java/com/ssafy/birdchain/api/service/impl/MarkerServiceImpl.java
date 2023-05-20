package com.ssafy.birdchain.api.service.impl;

import com.ssafy.birdchain.api.service.ImageService;
import com.ssafy.birdchain.api.service.MarkerService;
import com.ssafy.birdchain.common.db.dto.request.marker.MarkerAddReqDTO;
import com.ssafy.birdchain.common.db.dto.request.marker.MarkerDeleteReqDTO;
import com.ssafy.birdchain.common.db.dto.request.marker.MarkerModifyReqDTO;
import com.ssafy.birdchain.common.db.dto.response.marker.MarkerAllResDTO;
import com.ssafy.birdchain.common.db.dto.response.marker.MarkerResDTO;
import com.ssafy.birdchain.common.db.entity.Marker;
import com.ssafy.birdchain.common.db.repository.MarkerRepository;
import com.ssafy.birdchain.common.exception.CommonApiException;
import com.ssafy.birdchain.common.exception.errorcode.CommonErrorCode;
import com.ssafy.birdchain.common.util.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MarkerServiceImpl implements MarkerService {

    private final MarkerRepository markerRepository;
    private final ImageService imageService;

    /**
     * 유형별 마커 목록 조회
     *
     * @param type
     * @return List<MarkerAllResDTO>
     */
    @Override
    public List<MarkerAllResDTO> findAllMarker(String type) {
        return markerRepository.findAllByType(type);
    }


    /**
     * 마커 상세 조회
     *
     * @param id
     * @return
     */
    @Override
    public MarkerResDTO findMarker(Long id) {
        Marker marker = markerRepository.findByIdAndStatus(id, true).orElseThrow(() -> new CommonApiException(CommonErrorCode.MARKER_NOT_FOUND));
        return MarkerResDTO.builder()
                .id(marker.getId())
                .type(marker.getType())
                .nickname(marker.getNickname())
                .lat(marker.getLat())
                .lng(marker.getLng())
                .location(marker.getLocation())
                .image(marker.getImage())
                .content(marker.getContent())
                .createdAt(marker.getCreatedAt())
                .build();
    }


    /**
     * 마커 등록
     *
     * @param markerAddReqDTO
     * @throws IOException
     */
    @Override
    public void addMarker(MarkerAddReqDTO markerAddReqDTO, MultipartFile multipartFile) throws IOException {
        String imageUrl = imageService.upload(multipartFile, "images");

        Marker marker = Marker.builder()
                .nickname(markerAddReqDTO.getNickname())
                .lat(markerAddReqDTO.getLat())
                .lng(markerAddReqDTO.getLng())
                .location(markerAddReqDTO.getLocation())
                .type(markerAddReqDTO.getType())
                .content(markerAddReqDTO.getContent())
                .password(markerAddReqDTO.getPassword())
                .image(imageUrl)
                .status(true)
                .build();
        markerRepository.save(marker);
    }

    /**
     * 마커 수정
     *
     * @param markerModifyReqDTO
     */
    @Override
    public void modifyMarker(MarkerModifyReqDTO markerModifyReqDTO, MultipartFile multipartFile) throws IOException {
        Marker marker = markerRepository.findById(markerModifyReqDTO.getId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.MARKER_NOT_FOUND));
        if (marker.getNickname().equals(markerModifyReqDTO.getNickname()) && marker.getPassword().equals(markerModifyReqDTO.getPassword())) {
            if (!multipartFile.isEmpty()) {
                String imageUrl = imageService.upload(multipartFile, "images");
                marker.setImage(imageUrl);
            }
            marker.setContent(markerModifyReqDTO.getContent());
            markerRepository.save(marker);
        } else {
            throw new CommonApiException(CommonErrorCode.MARKER_NOT_ALLOWED);
        }
    }

    /**
     * 마커 삭제
     *
     * @param markerDeleteReqDTO
     */
    @Override
    public void deleteMarker(MarkerDeleteReqDTO markerDeleteReqDTO) {
        Marker marker = markerRepository.findById(markerDeleteReqDTO.getId()).orElseThrow(() -> new CommonApiException(CommonErrorCode.MARKER_NOT_FOUND));
        if (marker.getNickname().equals(markerDeleteReqDTO.getNickname()) && marker.getPassword().equals(markerDeleteReqDTO.getPassword())) {
            marker.setStatus(false);
        } else {
            throw new CommonApiException(CommonErrorCode.MARKER_NOT_ALLOWED);
        }
    }

    @Override
    public boolean validImgFile(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!multipartFile.isEmpty()) {
                boolean isValid = FileValidator.validImgFile(inputStream);
                if (!isValid) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

}
