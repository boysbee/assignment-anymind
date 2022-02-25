# assignment-anymind

## Design


### Prerequisites
- JDK 11
- Docker
- Maven
- Postgres

### Usage:
- By manual build & run docker compose
- Use script build and run docker compose

### Manual Build and Run
1. build docker image at wallet-deposit
2. build docker image at wallet-history
3. start docker compose

#### build wallet-deposit
```shell
cd wallet-deposit
mvn spring-boot:build-image
```

#### build wallet-history
```shell
cd wallet-history
mvn spring-boot:build-image
```

#### run docker-compose
```shell
docker-compose up --build
```

### Use script to build and run
1. Build image with script `build.sh`
2. Start to run docker compose

#### use script build images
```shell
sh build.sh
```

#### run docker-compose

```shell
docker-compose up --build
```

### Features
- Save record
- Get history of your wallet balance at the end of each `hour` between two dates

### Usage API
- Deposit coin
- Show history of current balance of each `hour`.
#### Save Record
API Detail

| Key          | Description            |
|:-------------|:-----------------------|
| HOST         | 127.0.0.1 or localhost |
| PORT         | 8080                   |
| Context path | wallet-deposit         |
| URI          | /api/wallet            |

Request Body

| Field Name | Type   | Description                                                                 | Required                    | Example                   |
|:-----------|:-------|:----------------------------------------------------------------------------|:----------------------------|:--------------------------|
| datetime   | string | The transaction date time with timezone to save record                      | Yes         | 2022-02-24T00:00:00+00:00 |
| amount     | number | The amount of save record, that validate not less than 0 and not more than 9999999999.99999 | Yes         | 10.00                     |

```json
{
  "datetime": "2022-02-25T13:48:01+01:00",
  "amount": 1.0
}
```

##### Usage by curl
```shell
curl --location --request POST 'http://localhost:8080/wallet-deposit/api/wallet' \
--header 'Content-Type: application/json' \
--data-raw '{
    "datetime": "2022-02-25T13:48:01+01:00",
    "amount": 1.0
}'

```

#### Show wallet balance history of each `hour`

API Detail

| Name         | Description            |
|:-------------|:-----------------------|
| HOST         | 127.0.0.1 or localhost |
| PORT         | 8081                   |
| Context path | wallet-history         |
| URI          | /api/wallet            |

##### Request Parameter

| Parameter Name | Type           | Description                                                                                                                                                 | Required                    | Example |
|:---------------|:---------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------|:-----------------------|
| startDatetime  | Date           | Start date time to find current balance history. This value should validate with date time format is `yyyy-MM-dd'T'HH:mm:ssxxx`.                            | Yes         | 2022-02-24T00:00:00%2B00:00 |
| endDatetime    | Date           | End date time to find current balance history. This value should be validate with date time format is `yyyy-MM-dd'T'HH:mm:ssxxx`. | Yes         | 2022-02-25T00:00:00%2B00:00 |

##### Usage by curl
```shell
curl --location --request GET 'http://localhost:8081/wallet-history/api/wallet?startDatetime=2022-02-24T00:00:00%2B00:00&endDatetime=2022-02-26T00:00:00%2B00:00'
```