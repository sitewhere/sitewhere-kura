# SiteWhere Kura Cloud Connector

SiteWhere Kura Cloud Connector provides an implementation of 
[Cloud Connection Model](https://github.com/eclipse/kura/wiki/Generic-Cloud-Services:-cloud-connection-developer-guide)
introduced by [Kura](https://github.com/eclipse/kura) 4.0.0 to connecto [SiteWhere 2.0](http://sitewhere.io/docs/2.0.0/)

## Build the Bundle

In order to build SiteWhere Kura Cloud Connector you need an Eclipse Kura Workspace. You can download Eclipse
Kura Workspace for your platform of choice from [here](https://www.eclipse.org/kura/downloads.php).

```sh
mvn clean install -Dkura.basedir=<path_to_kura_workspace>
```
