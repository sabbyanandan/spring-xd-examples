WordCount using Spring XD
=========================

This is an example of a "WordCount" built as a custom module that can be complied and packaged as a simple jar file and used in Spring XD runtime environment.

**Custom Module Upload**
> xd:>module upload --file /PATH_TO_YOUR_ARTIFACT/wordcount-1.0.0.BUILD-SNAPSHOT.jar --name wordcount --type processor

```
  Successfully uploaded module 'processor:wordcount'
```

**Display Module Metadata Options**
> xd:>module info processor:wordcount

---
 
**Solution #1: [Using HTTP Source Module](https://github.com/spring-projects/spring-xd/wiki/Sources#http) - Stream with Custom Processor** 
> xd:>stream create foo --definition "http | wordcount | log" --deploy

```
Created and deployed new stream 'foo
```

**Sample Data**
> xd:>http post --data "How much wood would a woodchuck chuck if a woodchuck could chuck wood"

```
12:40:38,211 1.1.0.M2  INFO inbound.foo.1-redis:queue-inbound-channel-adapter1 sink.foo - {How=1, a=2, would=1, woodchuck=2, could=1, wood=2, chuck=2, if=1, much=1}
```

---

**Solution #2: [Using File Source Module](https://github.com/spring-projects/spring-xd/wiki/Sources#file) - Stream with Custom Processor**
> xd:>stream create bar --definition "file --outputType=text/plain | wordcount | log" --deploy

**Sample Data**

```
The file source by default will look into a directory named after the stream, in this case /tmp/xd/input/bar
```

```
11:03:39,052 1.1.0.M2  INFO inbound.bar.1-redis:queue-inbound-channel-adapter1 sink.bar - {all=1, a=6, woodchuck=6, could=4, Well,=1, the=1, How=1, that=1, would=2, chuck,=1, wood!=1, wood=3, chuck=5, if=2, If=1, much=1, wood,=1}
```



