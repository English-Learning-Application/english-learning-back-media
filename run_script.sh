aws ecr get-login-password --region ap-southeast-2 | docker login --username AWS --password-stdin 761018889743.dkr.ecr.ap-southeast-2.amazonaws.com
docker build -t media-microservice .
docker tag media-microservice:latest 761018889743.dkr.ecr.ap-southeast-2.amazonaws.com/media-microservice:latest
docker push 761018889743.dkr.ecr.ap-southeast-2.amazonaws.com/media-microservice:latest
kubectl delete deployment media-service-deployment
kubectl apply -f deployment.yaml