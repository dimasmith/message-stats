SMS Stats
=========

Process stats from SmsBackupRestore export files.

# Running

It is standard spring boot application. So it can be started with
```
gradlew bootRun
```

## Parameters
| Property | Env | Meaning | 
| --- | --- | --- |
| `import.url` | `IMPORT_URL` | URL or file where xml file with messages is present |

# REST

Configured statistics endpoints:

Count messages by sender
```
/v1/stats/bySender
```

Count messages by day of week
```
/v1/stats/byDayOfWeek
```

Count messages by month
```
/v1/stats/byMonth
```
