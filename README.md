### 🚀 Selecione o perfil do app

- prd
- hml
- dev = default

```
bootRun --args='--spring.profiles.active=prd'
```

### 🚀 Start/Down ambiente local

```
docker-compose -f devops/docker-compose.yaml up -d --build
```

```
docker-compose -f devops/docker-compose.yaml up down
```

### 📌 Acessar app local

#### 🌏 App

- ```
  http://localhost:8080/api
  ```

#### 🌏 OpenApi UI

- ```
  http://localhost:8080/api/doc-ui
  ```

#### 🌏 OpenApi API

- ```
  http://localhost:8080/api/doc-api
  ```

#### 🌏 OpenApi API YAML

- ```
  http://localhost:8080/api/doc-api.yaml
  ```

### ⛓️ Metricas

#### 🌏 Requests

- ```
  ```

- ```
  ```

#### 🌏 Hikari

- ```
  ```
- ```
  ```

- ```
  ```
