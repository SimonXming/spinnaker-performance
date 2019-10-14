
# Usage

## Docker image

```
docker build -t spinnaker-performance:latest .
```

## Manually setup

### Install sdkman

```
curl -s "https://get.sdkman.io" | bash
source "/home/worker/.sdkman/bin/sdkman-init.sh"
```

### Install Gradle

```
sdk install gradle 3.1
# disable the Gradle Daemon?
# https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:ways_to_disable_gradle_daemon
```

### Install dependences

```
sudo yum install java-1.8.0-openjdk-devel
gradle wrapper
```

### List all tasks

```
gradle task
gradle perf
```
