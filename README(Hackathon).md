# Snowplow Event Generator For Commerce

This is used to generate custom snowplow events that can be used in testing.

To start developing, add your context under `core/src/main/scala/com.snowplowanalytics.snowplow.eventgenerator/contexts/` and add your event under `core/src/main/scala/com.snowplowanalytics.snowplow.eventgenerator/protocol/context/Your-Contxt`.

## How to run
First, compile the project by running `sbt assembly`.

Then, run the following command to generate events:
```sinks/target/scala-2.13/snowplow-event-generator --config config/kc-config.hocon```

kc-config.hocon is the configuration file that contains the configuration for the event generator.