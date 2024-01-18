#FROM ubuntu:jammy
#FROM --platform=linux/arm64/v8 ghcr.io/graalvm/jdk-community:21
FROM --platform=linux/arm64/v8 bellsoft/liberica-openjdk-alpine:21
COPY target/virtual-graal /virtual-graal
CMD ./virtual-graal

#docker buildx  build -t virtual-graal:latest . --platform linux/arm64/v8
#docker run -p 9195:9195 --name virtual-graal virtual-graal:latest
#docker build -t virtual-graal:latest-arm64 .