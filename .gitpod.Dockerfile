FROM gitpod/workspace-full

# Install custom tools, runtimes, etc.
# For example "bastet", a command-line tetris clone:
# RUN brew install bastet
#
# More information: https://www.gitpod.io/docs/config-docker/


RUN curl -s "https://get.sdkman.io" | bash
RUN $HOME/.sdkman/bin/sdkman-init.sh
RUN sdk install java 20.3.0.r11-grl"
