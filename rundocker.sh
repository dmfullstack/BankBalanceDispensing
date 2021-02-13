sudo docker build -t dineshmetkari/bankbalancedispensing:1.0 .
sudo docker push dineshmetkari/bankbalancedispensing:1.0
sudo docker run -p 8080:8080 dineshmetkari/bankbalancedispensing:1.0
