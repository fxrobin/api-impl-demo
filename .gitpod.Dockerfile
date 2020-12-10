FROM gitpod/workspace-full

# Install custom tools, runtimes, etc.
# For example "bastet", a command-line tetris clone:
# RUN brew install bastet
#
# More information: https://www.gitpod.io/docs/config-docker/

USER gitpod

RUN sudo apt-get update
RUN sudo apt-get install build-essential libz-dev zlib1g-dev

RUN curl -s "https://get.sdkman.io" | bash
RUN chmod +x $HOME/.sdkman/bin/sdkman-init.sh
RUN bash -c 'source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 20.3.0.r11-grl < /dev/null && gu install native-image'
