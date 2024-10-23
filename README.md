##Install 

cd auth-service  
helm install auth-app helm -n otus --create-namespace

cd ../gateway-service  
helm install gateway-app helm -n otus

cd ../user-service  
helm install user-app helm -n otus

cd ../kafka
helm install kafka helm -n otus

cd ../order-service  
helm install order-app helm -n otus

cd ../billing-service  
helm install billing-app helm -n otus

cd ../notification-service  
helm install notification-app helm -n otus