Scheduling Regular Ingress
==========================

This example demonstrates Spring XD's workflow orchestration capabilities. A common data pipeline use case is to download artifacts and write it somewhere. Downstream data processing events can be further chained in Spring XD to create complex enterprise workflows. Specifically, in this example, a data pipeline is created and scheduled to poll a directory and write the matching artifacts into Hadoop/HDFS. The schedule interval (in milliseconds) is customizable; for instance, it can be in minutes or in days.      

**Hadoop Configuration:**

Refer to [Hadoop installation](https://github.com/spring-projects/spring-xd/wiki/Hadoop-Installation) for more details. It is assumed that _namenode_ and _datanode_ processes are running along with write access as explained.

--

**Stream Definition:**

It is also assumed that Spring XD is running either in singlenode or as distributed prior to the following steps.

Following stream definition polls default directory (in this case: xd/input/urldownload) for files with extension '.txt' and a _fixedDelay_ of 5s (i.e: poll every 5 secs). Once the data is in pipeline, writes them in HDFS (again with the defaults). This will be a continuous stream unless otherwise destroyed or undeployed.  

> xd:>stream create urldownload --definition "file --outputType=text/plain --pattern=*.txt --fixedDelay=5 | hdfs" --deploy

```
Created and deployed new stream 'urldownload'
```

**Spring XD Container View [admin-ui]:** _(verify container state - modules deployed and ready for ingress)_

![Ingress Ready](/regular-ingress/resources/file-hdfs-ready.png)
---

**Copy the Data File to Polling Directory:** _(in this case: xd/input/urldownload)_ 

Sample data file [urls.txt](/regular-ingress/resources/urls.txt) contains url's.

--

**List HDFS Directory:** 

>xd:>hadoop config fs --namenode hdfs://localhost:8020


>xd:>hadoop fs ls /xd/urldownload

**Hadoop NameNode UI and Directory Listing:** _(verify in-progress state)_

![DIR Listing](/regular-ingress/resources/temp-stage.png)
---

**Verify Writes:** 

The deployed stream in essence can be a continuous data pipeline with a schedule to poll the directory and write based on a timed interval (ex: 24hrs once). Unless overridden, the default _rollover_ setting for HDFS sink in Spring XD is 1GB. The writes wouldn't complete until it reaches this size; hence, we close the stream to verify the results in HDFS.   

> xd:>stream destroy urldownload

```
Destroyed stream 'urldownload'
```

**Spring XD Container View [admin-ui]:** _(verify message rates)_

![Complete](/regular-ingress/resources/ingress-complete.png)

**Hadoop NameNode UI and Directory Listing:** _(verify the contents from urldownload.txt file)_
![Ingress Complete](/regular-ingress/resources/file-hdfs-complete.png)





