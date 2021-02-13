sudo docker build -t dineshmetkari/bankbalancedispensing:latest .
sudo docker push dineshmetkari/bankbalancedispensing:latest
sudo docker run -p 8080:8080 dineshmetkari/bankbalancedispensing:latest
