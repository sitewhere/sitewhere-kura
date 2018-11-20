#/bin/bash

KURA_WORKSPACE='http://ftp.ussg.iu.edu/eclipse/kura/releases/4.0.0/user_workspace_archive_4.0.0.zip'
KURA_WORKSPACE_FILE_NAME='user_workspace_archive_4.0.0.zip'
KURA_WORKSPACE_DIR=$PWD/kura_workspace
SITEWHERE_KCC='com.sitewhere.cloud'

wget -O$KURA_WORKSPACE_FILE_NAME $KURA_WORKSPACE 

cleanup() {
  rm -rf $KURA_WORKSPACE_DIR
  rm $KURA_WORKSPACE_FILE_NAME
}

if [ -ne $KURA_WORKSPACE_FILE_NAME ]; then
  echo "File $KURA_WORKSPACE_FILE_NAME does not exist."
  exit 1;
else
  echo "File $KURA_WORKSPACE_FILE_NAME exists."

  rm -rf $KURA_WORKSPACE_DIR
  unzip $KURA_WORKSPACE_FILE_NAME -d $KURA_WORKSPACE_DIR

  echo "Building SiteWhere Kura Cloud Connector ..."
  
  pushd $SITEWHERE_KCC
  mvn clean install -Dkura.basedir=$KURA_WORKSPACE_DIR
  popd
  cleanup();
  exit 0;
fi
