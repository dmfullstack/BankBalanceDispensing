docker build -t dineshmetkari/bankbalancedispensing:latest .
docker push dineshmetkari/bankbalancedispensing:latest
docker run -p 8080:8080 dineshmetkari/bankbalancedispensing:latest
