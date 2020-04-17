ARG WORK_DIR=/opt
ARG TEMP_DIR=/temp
ARG JAVA_NAME="haidman-security-web.jar"

#来自镜像基础
FROM java:8
#指定镜像创建者信息
MAINTAINER haidman
#切换到工作目录
#WORKDIR ${WORK_DIR}
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
#VOLUME ${TEMP_DIR}
#将本地jar 复制并且解压到容器中的 ${WORK_DIR_JAR} 目录
ADD ${WORK_DIR}/${JAVA_NAME} ${TEMP_DIR}/${JAVA_NAME}
#运行jar
RUN bash -c "touch /"${JAVA_NAME}
ENTRYPOINT ["java", "-jar", "haidman-security-web.jar", "> /log/app.log"]