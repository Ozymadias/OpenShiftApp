export PATH=$PATH:~/Downloads/minishift-1.15.1-linux-amd64
eval $(~/Downloads/minishift-1.15.1-linux-amd64/minishift oc-env)
oc cluster up
sleep 5
oc login -u developer
docker login -u `oc whoami` -p `oc whoami -t` 172.30.1.1:5000
docker tag docker-spring-boot-hello 172.30.1.1:5000/myproject/hello