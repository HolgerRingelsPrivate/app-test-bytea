# Load base image
FROM tiredofit/nodejs:16-alpine-16.17.0

WORKDIR /usr/src/myapp
COPY start/ /usr/src/myapp

RUN npm install -g serve

# Executing the app
CMD ["./Startup.sh"]