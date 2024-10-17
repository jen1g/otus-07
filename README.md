##Install 

cd auth-service  
helm install otus-auth helm -n app --create-namespace

cd gateway-service  
helm install otus-gateway helm -n app

cd user-service  
helm install otus-user helm -n app