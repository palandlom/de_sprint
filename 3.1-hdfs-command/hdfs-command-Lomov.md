
Выполнялось в ubuntu 18.04

```bash
docker pull cloudera/quickstart:latest

user@dot:~$ docker images
REPOSITORY            TAG       IMAGE ID       CREATED       SIZE
cloudera/quickstart   latest    4239cd2958c6   6 years ago   6.34GB

user@dot:~$ docker run --hostname=quickstart.cloudera --privileged=true -t -i -p 8888:8888 4239cd2958c6  /usr/bin/docker-quickstart
Starting mysqld:                                           [  OK  ]

if [ "$1" == "start" ] ; then
    if [ "${EC2}" == 'true' ]; then
        FIRST_BOOT_FLAG=/var/lib/cloudera-quickstart/.ec2-key-installed
        if [ ! -f "${FIRST_BOOT_FLAG}" ]; then
            METADATA_API=http://169.254.169.254/latest/meta-data
            KEY_URL=${METADATA_API}/public-keys/0/openssh-key
            SSH_DIR=/home/cloudera/.ssh
            mkdir -p ${SSH_DIR}
            chown cloudera:cloudera ${SSH_DIR}
            curl ${KEY_URL} >> ${SSH_DIR}/authorized_keys
            touch ${FIRST_BOOT_FLAG}
        fi
    fi
    if [ "${DOCKER}" != 'true' ]; then
        if [ -f /sys/kernel/mm/redhat_transparent_hugepage/defrag ]; then
            echo never > /sys/kernel/mm/redhat_transparent_hugepage/defrag
        fi

        cloudera-quickstart-ip
        HOSTNAME=quickstart.cloudera
        hostname ${HOSTNAME}
        sed -i -e "s/HOSTNAME=.*/HOSTNAME=${HOSTNAME}/" /etc/sysconfig/network
    fi

    (
        cd /var/lib/cloudera-quickstart/tutorial;
        nohup python -m SimpleHTTPServer 80 &
    )

    # TODO: check for expired CM license and update config.js accordingly
fi
+ '[' start == start ']'
+ '[' '' == true ']'
+ '[' true '!=' true ']'
+ cd /var/lib/cloudera-quickstart/tutorial
+ nohup python -m SimpleHTTPServer 80

nohup: appending output to `nohup.out'
JMX enabled by default
Using config: /etc/zookeeper/conf/zoo.cfg
Starting zookeeper ... STARTED
starting datanode, logging to /var/log/hadoop-hdfs/hadoop-hdfs-datanode-quickstart.cloudera.out
Started Hadoop datanode (hadoop-hdfs-datanode):            [  OK  ]
starting journalnode, logging to /var/log/hadoop-hdfs/hadoop-hdfs-journalnode-quickstart.cloudera.out
Started Hadoop journalnode:                                [  OK  ]
starting namenode, logging to /var/log/hadoop-hdfs/hadoop-hdfs-namenode-quickstart.cloudera.out
Started Hadoop namenode:                                   [  OK  ]
starting secondarynamenode, logging to /var/log/hadoop-hdfs/hadoop-hdfs-secondarynamenode-quickstart.cloudera.out
Started Hadoop secondarynamenode:                          [  OK  ]

Setting HTTPFS_HOME:          /usr/lib/hadoop-httpfs
Using   HTTPFS_CONFIG:        /etc/hadoop-httpfs/conf
Sourcing:                    /etc/hadoop-httpfs/conf/httpfs-env.sh
Using   HTTPFS_LOG:           /var/log/hadoop-httpfs/
Using   HTTPFS_TEMP:           /var/run/hadoop-httpfs
Setting HTTPFS_HTTP_PORT:     14000
Setting HTTPFS_ADMIN_PORT:     14001
Setting HTTPFS_HTTP_HOSTNAME: quickstart.cloudera
Setting HTTPFS_SSL_ENABLED: false
Setting HTTPFS_SSL_KEYSTORE_FILE:     /var/lib/hadoop-httpfs/.keystore
Setting HTTPFS_SSL_KEYSTORE_PASS:     password
Using   CATALINA_BASE:       /var/lib/hadoop-httpfs/tomcat-deployment
Using   HTTPFS_CATALINA_HOME:       /usr/lib/bigtop-tomcat
Setting CATALINA_OUT:        /var/log/hadoop-httpfs//httpfs-catalina.out
Using   CATALINA_PID:        /var/run/hadoop-httpfs/hadoop-httpfs-httpfs.pid

Using   CATALINA_OPTS:       
Adding to CATALINA_OPTS:     -Dhttpfs.home.dir=/usr/lib/hadoop-httpfs -Dhttpfs.config.dir=/etc/hadoop-httpfs/conf -Dhttpfs.log.dir=/var/log/hadoop-httpfs/ -Dhttpfs.temp.dir=/var/run/hadoop-httpfs -Dhttpfs.admin.port=14001 -Dhttpfs.http.port=14000 -Dhttpfs.http.hostname=quickstart.cloudera
Using CATALINA_BASE:   /var/lib/hadoop-httpfs/tomcat-deployment
Using CATALINA_HOME:   /usr/lib/bigtop-tomcat
Using CATALINA_TMPDIR: /var/run/hadoop-httpfs
Using JRE_HOME:        /usr/java/jdk1.7.0_67-cloudera
Using CLASSPATH:       /usr/lib/bigtop-tomcat/bin/bootstrap.jar
Using CATALINA_PID:    /var/run/hadoop-httpfs/hadoop-httpfs-httpfs.pid
Started Hadoop httpfs (hadoop-httpfs):                     [  OK  ]
starting historyserver, logging to /var/log/hadoop-mapreduce/mapred-mapred-historyserver-quickstart.cloudera.out
22/11/19 15:00:15 INFO hs.JobHistoryServer: STARTUP_MSG: 
/************************************************************
STARTUP_MSG: Starting JobHistoryServer
STARTUP_MSG:   host = quickstart.cloudera/172.17.0.2
STARTUP_MSG:   args = []
STARTUP_MSG:   version = 2.6.0-cdh5.7.0
STARTUP_MSG:   classpath = /etc/hadoop/conf:/usr/lib/hadoop/lib/jersey-core-1.9.jar:/usr/lib/hadoop/lib/activation-1.1.jar:/usr/lib/hadoop/lib/hue-plugins-3.9.0-cdh5.7.0.jar:/usr/lib/hadoop/lib/commons-net-3.1.jar:/usr/lib/hadoop/lib/junit-4.11.jar:/usr/lib/hadoop/
    ............
    hadoop-mapreduce/lib/netty-3.6.2.Final.jar:/usr/lib/hadoop-mapreduce/lib/paranamer-2.3.jar:/usr/lib/hadoop-mapreduce/lib/protobuf-java-2.5.0.jar:/usr/lib/hadoop-mapreduce/lib/snappy-java-1.0.4.1.jar:/usr/lib/hadoop-mapreduce/lib/xz-1.0.jar:/usr/lib/hadoop-mapreduce/modules/*.jar
STARTUP_MSG:   build = http://github.com/cloudera/hadoop -r c00978c67b0d3fe9f3b896b5030741bd40bf541a; compiled by 'jenkins' on 2016-03-23T18:36Z
STARTUP_MSG:   java = 1.7.0_67
************************************************************/
Started Hadoop historyserver:                              [  OK  ]
starting nodemanager, logging to /var/log/hadoop-yarn/yarn-yarn-nodemanager-quickstart.cloudera.out
Started Hadoop nodemanager:                                [  OK  ]
starting resourcemanager, logging to /var/log/hadoop-yarn/yarn-yarn-resourcemanager-quickstart.cloudera.out
Started Hadoop resourcemanager:                            [  OK  ]
starting master, logging to /var/log/hbase/hbase-hbase-master-quickstart.cloudera.out
Started HBase master daemon (hbase-master):                [  OK  ]
starting rest, logging to /var/log/hbase/hbase-hbase-rest-quickstart.cloudera.out
Started HBase rest daemon (hbase-rest):                    [  OK  ]
starting thrift, logging to /var/log/hbase/hbase-hbase-thrift-quickstart.cloudera.out
Started HBase thrift daemon (hbase-thrift):                [  OK  ]
Starting Hive Metastore (hive-metastore):                  [  OK  ]
Started Hive Server2 (hive-server2):                       [  OK  ]
Starting Sqoop Server:                                     [  OK  ]
Sqoop home directory: /usr/lib/sqoop2
Setting SQOOP_HTTP_PORT:     12000
Setting SQOOP_ADMIN_PORT:     12001
Using   CATALINA_OPTS:       -Xmx1024m
Adding to CATALINA_OPTS:    -Dsqoop.http.port=12000 -Dsqoop.admin.port=12001
Using CATALINA_BASE:   /var/lib/sqoop2/tomcat-deployment
Using CATALINA_HOME:   /usr/lib/bigtop-tomcat
Using CATALINA_TMPDIR: /var/tmp/sqoop2
Using JRE_HOME:        /usr/java/jdk1.7.0_67-cloudera
Using CLASSPATH:       /usr/lib/bigtop-tomcat/bin/bootstrap.jar
Using CATALINA_PID:    /var/run/sqoop2/sqoop-server-sqoop2.pid
Starting Spark history-server (spark-history-server):      [  OK  ]
Starting Hadoop HBase regionserver daemon: starting regionserver, logging to /var/log/hbase/hbase-hbase-regionserver-quickstart.cloudera.out
hbase-regionserver.
Starting hue:                                              [FAILED]
Started Impala State Store Server (statestored):           [  OK  ]

Setting OOZIE_HOME:          /usr/lib/oozie
Sourcing:                    /usr/lib/oozie/bin/oozie-env.sh
  setting JAVA_LIBRARY_PATH="$JAVA_LIBRARY_PATH:/usr/lib/hadoop/lib/native"
  setting OOZIE_DATA=/var/lib/oozie
  setting OOZIE_CATALINA_HOME=/usr/lib/bigtop-tomcat
  setting CATALINA_TMPDIR=/var/lib/oozie
  setting CATALINA_PID=/var/run/oozie/oozie.pid
  setting CATALINA_BASE=/var/lib/oozie/tomcat-deployment
  setting OOZIE_HTTPS_PORT=11443
  setting OOZIE_HTTPS_KEYSTORE_PASS=password
  setting CATALINA_OPTS="$CATALINA_OPTS -Doozie.https.port=${OOZIE_HTTPS_PORT}"
  setting CATALINA_OPTS="$CATALINA_OPTS -Doozie.https.keystore.pass=${OOZIE_HTTPS_KEYSTORE_PASS}"
  setting CATALINA_OPTS="$CATALINA_OPTS -Xmx1024m"
  setting OOZIE_CONFIG=/etc/oozie/conf
  setting OOZIE_LOG=/var/log/oozie
Using   OOZIE_CONFIG:        /etc/oozie/conf
Sourcing:                    /etc/oozie/conf/oozie-env.sh
  setting JAVA_LIBRARY_PATH="$JAVA_LIBRARY_PATH:/usr/lib/hadoop/lib/native"
  setting OOZIE_DATA=/var/lib/oozie
  setting OOZIE_CATALINA_HOME=/usr/lib/bigtop-tomcat
  setting CATALINA_TMPDIR=/var/lib/oozie
  setting CATALINA_PID=/var/run/oozie/oozie.pid
  setting CATALINA_BASE=/var/lib/oozie/tomcat-deployment
  setting OOZIE_HTTPS_PORT=11443
  setting OOZIE_HTTPS_KEYSTORE_PASS=password
  setting CATALINA_OPTS="$CATALINA_OPTS -Doozie.https.port=${OOZIE_HTTPS_PORT}"
  setting CATALINA_OPTS="$CATALINA_OPTS -Doozie.https.keystore.pass=${OOZIE_HTTPS_KEYSTORE_PASS}"
  setting CATALINA_OPTS="$CATALINA_OPTS -Xmx1024m"
  setting OOZIE_CONFIG=/etc/oozie/conf
  setting OOZIE_LOG=/var/log/oozie
Setting OOZIE_CONFIG_FILE:   oozie-site.xml
Using   OOZIE_DATA:          /var/lib/oozie
Using   OOZIE_LOG:           /var/log/oozie
Setting OOZIE_LOG4J_FILE:    oozie-log4j.properties
Setting OOZIE_LOG4J_RELOAD:  10
Setting OOZIE_HTTP_HOSTNAME: quickstart.cloudera
Setting OOZIE_HTTP_PORT:     11000
Setting OOZIE_ADMIN_PORT:     11001
Using   OOZIE_HTTPS_PORT:     11443
Setting OOZIE_BASE_URL:      http://quickstart.cloudera:11000/oozie
Using   CATALINA_BASE:       /var/lib/oozie/tomcat-deployment
Setting OOZIE_HTTPS_KEYSTORE_FILE:     /var/lib/oozie/.keystore
Using   OOZIE_HTTPS_KEYSTORE_PASS:     password
Setting OOZIE_INSTANCE_ID:       quickstart.cloudera
Setting CATALINA_OUT:        /var/log/oozie/catalina.out
Using   CATALINA_PID:        /var/run/oozie/oozie.pid

Using   CATALINA_OPTS:        -Doozie.https.port=11443 -Doozie.https.keystore.pass=password -Xmx1024m -Doozie.https.port=11443 -Doozie.https.keystore.pass=password -Xmx1024m -Dderby.stream.error.file=/var/log/oozie/derby.log
Adding to CATALINA_OPTS:     -Doozie.home.dir=/usr/lib/oozie -Doozie.config.dir=/etc/oozie/conf -Doozie.log.dir=/var/log/oozie -Doozie.data.dir=/var/lib/oozie -Doozie.instance.id=quickstart.cloudera -Doozie.config.file=oozie-site.xml -Doozie.log4j.file=oozie-log4j.properties -Doozie.log4j.reload=10 -Doozie.http.hostname=quickstart.cloudera -Doozie.admin.port=11001 -Doozie.http.port=11000 -Doozie.https.port=11443 -Doozie.base.url=http://quickstart.cloudera:11000/oozie -Doozie.https.keystore.file=/var/lib/oozie/.keystore -Doozie.https.keystore.pass=password -Djava.library.path=:/usr/lib/hadoop/lib/native:/usr/lib/hadoop/lib/native

Using CATALINA_BASE:   /var/lib/oozie/tomcat-deployment
Using CATALINA_HOME:   /usr/lib/bigtop-tomcat
Using CATALINA_TMPDIR: /var/lib/oozie
Using JRE_HOME:        /usr/java/jdk1.7.0_67-cloudera
Using CLASSPATH:       /usr/lib/bigtop-tomcat/bin/bootstrap.jar
Using CATALINA_PID:    /var/run/oozie/oozie.pid
Starting Solr server daemon:                               [  OK  ]
Using CATALINA_BASE:   /var/lib/solr/tomcat-deployment
Using CATALINA_HOME:   /usr/lib/solr/../bigtop-tomcat
Using CATALINA_TMPDIR: /var/lib/solr/
Using JRE_HOME:        /usr/java/jdk1.7.0_67-cloudera
Using CLASSPATH:       /usr/lib/solr/../bigtop-tomcat/bin/bootstrap.jar
Using CATALINA_PID:    /var/run/solr/solr.pid
Started Impala Catalog Server (catalogd) :                 [  OK  ]
Started Impala Server (impalad):                           [  OK  ]
[root@quickstart /]# 
```
• После того, как файлы окажутся на HDFS попробуйте выполнить команду, которая выводит содержимое папки. Особенно обратите внимание на права доступа к вашим файлам. 
```bash
[root@quickstart /]# /usr/bin/hdfs dfs -ls /user/cloudera/
Found 2 items
-rw-r--r--   1 cloudera cloudera       6556 2022-11-19 15:16 /user/cloudera/image_2022-03-03_13-59-07.png
-rw-r--r--   1 cloudera cloudera    3798944 2022-11-19 15:15 /user/cloudera/ontology_engeneering_book.pdf

```
• Далее сожмите все 4 тома в 1 файл.
```bash
[root@quickstart /]# /usr/bin/hadoop fs -ls /user/cloudera
Found 2 items
-rw-r--r--   1 cloudera cloudera       6556 2022-11-19 15:16 /user/cloudera/image_2022-03-03_13-59-07.png
-rw-r--r--   1 cloudera cloudera    3798944 2022-11-19 15:15 /user/cloudera/ontology_engeneering_book.pdf
[root@quickstart /]# /usr/bin/hadoop fs -getmerge /user/cloudera ~/merged_dile
[root@quickstart /]# ls
bin  boot  dev  etc  home  lib  lib64  lost+found  media  mnt  opt  packer-files  proc  root  sbin  selinux  srv  sys  tmp  usr  var
[root@quickstart /]# cd /root
[root@quickstart ~]# ls
hue.json  merged_dile

```
• Теперь давайте изменим права доступа к нашему файлу. Чтобы с нашим файлом могли взаимодействовать коллеги, установите режим доступа, который дает полный доступ для владельца файла, а для сторонних пользователей возможность читать и выполнять.
• Попробуйте заново использовать команду для вывода содержимого папки и обратите внимание как изменились права доступа к файлу.
```bash
[root@quickstart ~]# sudo -u hdfs /usr/bin/hdfs dfs -chmod 755  /user/cloudera/image_2022-03-03_13-59-07.png
[root@quickstart ~]# /usr/bin/hdfs dfs -ls  /user/cloudera/
Found 2 items
-rwxr-xr-x   1 cloudera cloudera       6556 2022-11-19 15:16 /user/cloudera/image_2022-03-03_13-59-07.png
-rw-r--r--   1 cloudera cloudera    3798944 2022-11-19 15:15 /user/cloudera/ontology_engeneering_book.pdf

```

• Теперь попробуем вывести на экран информацию о том, сколько места на диске занимает наш файл. Желательно, чтобы размер файла был удобночитаемым.
```bash
[root@quickstart ~]# /usr/bin/hdfs dfs -du -h  /user/cloudera/image_2022-03-03_13-59-07.png
6.4 K  6.4 K  /user/cloudera/image_2022-03-03_13-59-07.png

```
• На экране вы можете заметить 2 числа. Первое число – это фактический размер файла, а второе – это занимаемое файлом место на диске с учетом репликации. По умолчанию в данной версии HDFS эти числа будут одинаковы – это означает, что никакой репликации нет – нас это не очень устраивает, мы хотели бы, чтобы у наших файлов существовали резервные копии, поэтому напишите команду, которая изменит фактор репликации на 2.
• Повторите команду, которая выводит информацию о том, какое место на диске занимает файл и убедитесь, что изменения произошли.
```bash
[root@quickstart ~]# /usr/bin/hdfs dfs -setrep 4 /user/cloudera/image_2022-03-03_13-59-07.png
Replication 4 set: /user/cloudera/image_2022-03-03_13-59-07.png
[root@quickstart ~]# /usr/bin/hdfs dfs -du -h  /user/cloudera/image_2022-03-03_13-59-07.png
6.4 K  25.6 K  /user/cloudera/image_2022-03-03_13-59-07.png

```

• Напишите команду, которая подсчитывает количество строк в вашем файле 
```bash
[root@quickstart ~]# /usr/bin/hdfs dfs -text  /user/cloudera/image_2022-03-03_13-59-07.png | wc -l
25
```
