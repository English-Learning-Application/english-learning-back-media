docker build -t media-microservice .
minikube image load media-microservice:latest
kubectl delete secret media-service-secret
kubectl create secret generic media-service-secret --from-env-file=local.env
kubectl delete deployment media-service-deployment
kubectl apply -f local-deployment.yaml