# spark-cat-boost
## sbt

<a href="https://catboost.ai/en/docs/concepts/spark-overview">
    CatBoost.ai
</a>

### <a href="https://github.com/GoogleCloudPlatform/spark-on-k8s-operator/issues/815">Issue</a>

```
Spark cluster configuration
CatBoost for Apache Spark requires one training task per executor. If you run training, you have to set
spark.task.cpus parameter to be equal to the number of cores in executors (spark.executor.cores).
This limitation might be relaxed in the future (the corresponding issue #1622).

Model application or feature importance evaluation do not have this limitation.
```
