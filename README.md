openssh-rsa-java [![build](https://travis-ci.org/daggerok/openssh-rsa-java.svg?branch=master)](https://travis-ci.org/daggerok/openssh-rsa-java)
================

build

```bash
gradle clean test -i
```

generate own ssh / RSA

```bash
mkdir -p src/main/resources/.ssh
ssh-keygen -t 'rsa' -C 'daggerok@gmail.com' -f src/main/resources/.ssh/id_rsa
Generating public/private rsa key pair.
... # just press enter few times to using empty password...
```
