# 1단계: 빌드용 이미지
FROM node:18.16.0-alpine AS build

# 작업 디렉토리 설정
WORKDIR /app_front

# 환경변수 추가
ARG VUE_APP_KAKAOMAP_KEY
ARG VUE_APP_API_URL
ARG VUE_APP_INFURA_API_KEY
ENV VUE_APP_KAKAOMAP_KEY=$VUE_APP_KAKAOMAP_KEY
ENV VUE_APP_API_URL=$VUE_APP_API_URL
ENV VUE_APP_NFURA_API_KEY=$VUE_APP_INFURA_API_KEY

# package.json과 package-lock.json 복사
COPY package*.json ./

# 종속성 설치
RUN npm install

# 프로젝트 파일 복사
COPY . .

# 프로젝트 빌드
RUN npm run build

# 2단계: 실행용 이미지
FROM nginx:stable-alpine

# 빌드 결과물을 Nginx로 복사
COPY --from=build /app_front/dist /usr/share/nginx/html

RUN rm /etc/nginx/conf.d/default.conf

# 변경된 Nginx 설정 복사
COPY ./default.conf /etc/nginx/conf.d/

# Nginx 포트 설정
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
