Custom Grouping of Modules
==========================

This example demonstrates the flexibility of Spring XD runtime. Custom grouping of modules helps with orchestration and as well as effective utilization of available resources in distributed deployment. The _http_ (source) and _log_ (sink) modules are 'pinned' to a specific node in the distributed Spring XD cluster (ex: Container **A** and Container **B**). _wordcount_ is a custom (processor) module used in the same stream; which however, follows default deployment strategy unlike the _http_ and _log_ modules.

**Display Module Metadata Options:** _(verify custom module is already deployed)_
> xd:>module info processor:wordcount

Module Info:
![Info](/wordcount-grouping/src/main/resources/module-info.png)
---
 
**Create Stream Definition: [Using HTTP Source Module](https://github.com/spring-projects/spring-xd/wiki/Sources#http)**
> xd:>stream create foo --definition "http | wordcount | log"

```
Created new stream 'foo'
```

**Container View [admin-ui]:** _(verify container state - empty)_

Empty Containers:
![Empty Containers](/custom-grouping/src/main/resources/empty-containers.png)
---

**Deployment Manifest with Grouping:** _(deploy 'http' and 'log' modules to container with label **B**)_
>stream deploy foo --properties "module.http.criteria=groups.contains('B'),module.log.criteria=groups.contains('B')"

```
Deployed stream 'foo'
```

**Container View [admin-ui]:** _(verify container state - grouping)_

Module Groupings:
![Module Groupings](/custom-grouping/src/main/resources/module_grouping.png)
---

**Deployment Manifest with Grouping and 'Scale Out':**
>xd:>stream deploy foo --properties "module.http.criteria=groups.contains('B'),module.log.criteria=groups.contains('B'),module.wordcount.count=2"

```
Deployed stream 'foo'
```

**Container View [admin-ui]:** _(verify container state - scaleout)_

Module Scale Out:
![Module Scale Out](/custom-grouping/src/main/resources/custom_module_scaleout.png)

**Sample Data:** _(verify logs from **A** and **B** containers)_
> xd:>http post --data "How much wood would a woodchuck chuck if a woodchuck could chuck wood"

```
12:54:43,705 1.1.0.M2  INFO inbound.foo.1-redis:queue-inbound-channel-adapter1 sink.foo - {How=1, a=2, would=1, woodchuck=2, could=1, wood=2, chuck=2, if=1, much=1}
```



