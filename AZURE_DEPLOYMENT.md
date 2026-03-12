# Azure App Service Deployment Configuration

## 🚀 Azure App Service Details

**Web App Name**: `blood-backend-api`  
**Java Version**: 17  
**Runtime Stack**: Java 17 (Java SE)  
**Region**: Choose based on your preference  

---

## 📋 Deployment Steps

### 1. **Create Azure App Service (if not exists)**

```bash
# Login to Azure
az login

# Create resource group
az group create --name blood-donor-rg --location eastus

# Create App Service plan
az appservice plan create \
  --name blood-donor-plan \
  --resource-group blood-donor-rg \
  --sku B1 \
  --is-linux

# Create Web App
az webapp create \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --plan blood-donor-plan \
  --runtime "JAVA:17-java17"
```

### 2. **Configure Startup Command**

In Azure Portal or via CLI, set the startup command:

#### Option A: Azure Portal
1. Go to **Azure Portal** → **App Services** → **blood-backend-api**
2. Navigate to **Configuration** → **General settings**
3. Set **Startup Command**:
   ```bash
   java -Dserver.port=$PORT -jar /home/site/wwwroot/*.jar
   ```
4. Click **Save** and restart the app

#### Option B: Azure CLI
```bash
az webapp config set \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --startup-file "java -Dserver.port=\$PORT -jar /home/site/wwwroot/*.jar"
```

### 3. **Get Publish Profile**

#### Via Azure Portal:
1. Go to **App Services** → **blood-backend-api**
2. Click **Get publish profile** (Download button)
3. Copy the XML content

#### Via Azure CLI:
```bash
az webapp deployment list-publishing-profiles \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --xml
```

### 4. **Add GitHub Secret**

1. Go to your GitHub repository: `blood-donor-finder-org/blood-donor-finder-backend`
2. Navigate to **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Name: `AZURE_WEBAPP_PUBLISH_PROFILE`
5. Value: Paste the entire publish profile XML content
6. Click **Add secret**

### 5. **Configure Environment Variables**

Set environment variables in Azure:

```bash
# Via Azure Portal: Configuration → Application settings
# Or via CLI:
az webapp config appsettings set \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --settings \
    SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-Xms512m -Xmx1024m"
```

### 6. **Deploy**

Push to main branch or manually trigger workflow:

```bash
git add .
git commit -m "fix: configure Azure deployment"
git push origin main
```

Or trigger manually in GitHub Actions tab.

---

## ⚙️ Application Configuration

### **Server Port Configuration**

The application automatically uses Azure's `$PORT` environment variable:

**application.properties**:
```properties
server.port=${PORT:8080}
```

This means:
- **Local development**: Uses port 8080
- **Azure deployment**: Uses Azure's dynamic PORT (typically 80 or 443)

### **Database Configuration**

Currently using H2 in-memory database. For production, configure Azure Database:

```bash
# Example: Azure Database for MySQL
az mysql flexible-server create \
  --name blood-donor-db \
  --resource-group blood-donor-rg \
  --location eastus \
  --admin-user dbadmin \
  --admin-password <YourPassword> \
  --sku-name Standard_B1ms
```

Then update environment variables:
```properties
SPRING_DATASOURCE_URL=jdbc:mysql://blood-donor-db.mysql.database.azure.com:3306/blooddonor
SPRING_DATASOURCE_USERNAME=dbadmin
SPRING_DATASOURCE_PASSWORD=<YourPassword>
```

---

## 🔍 Troubleshooting

### Issue: "ZIP Deploy failed"

**Causes:**
1. ❌ JAR file not properly built
2. ❌ Incorrect startup command
3. ❌ Wrong Java version
4. ❌ Missing publish profile secret

**Solutions:**
1. ✅ Verify JAR exists in workflow logs
2. ✅ Set correct startup command (see above)
3. ✅ Ensure Azure uses Java 17
4. ✅ Verify `AZURE_WEBAPP_PUBLISH_PROFILE` secret exists

### Issue: "Application not starting"

**Check logs:**
```bash
# Via Azure CLI
az webapp log tail \
  --name blood-backend-api \
  --resource-group blood-donor-rg

# Or stream logs
az webapp log download \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --log-file app-logs.zip
```

**Common fixes:**
- Verify startup command includes `$PORT`
- Check Java version matches (17)
- Ensure JAR is executable
- Review application logs for errors

### Issue: "Health check failing"

Add health endpoint check in Azure:

```bash
az webapp config set \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --health-check-path "/actuator/health"
```

Add Spring Boot Actuator to pom.xml if needed:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

## 📊 Deployment Verification

### 1. **Check Deployment Status**

GitHub Actions → azure-deploy.yml workflow → Check status

### 2. **Test API Endpoints**

```bash
# Health check
curl https://blood-backend-api.azurewebsites.net/actuator/health

# API endpoints (adjust based on your controllers)
curl https://blood-backend-api.azurewebsites.net/api/donors
```

### 3. **Monitor Application**

Azure Portal → App Services → blood-backend-api → Metrics

Key metrics to monitor:
- Response time
- HTTP requests
- Memory usage
- CPU percentage

---

## 🛠️ Manual Deployment (Alternative)

If GitHub Actions fails, deploy manually:

### Using Azure CLI:
```bash
# Build locally
mvn clean package -DskipTests

# Deploy JAR
az webapp deploy \
  --name blood-backend-api \
  --resource-group blood-donor-rg \
  --src-path target/blood-donor-finder-0.0.1-SNAPSHOT.jar \
  --type jar
```

### Using Azure Portal:
1. Build project: `mvn clean package`
2. Go to Azure Portal → App Services → blood-backend-api
3. Click **Deployment Center**
4. Choose **Local Git** or **FTP**
5. Upload JAR file to `/home/site/wwwroot/`

---

## 🔐 Security Best Practices

1. **Enable HTTPS only**:
   ```bash
   az webapp update \
     --name blood-backend-api \
     --resource-group blood-donor-rg \
     --https-only true
   ```

2. **Configure CORS** (already in application.properties):
   - Only allow trusted origins
   - Update `app.cors.allowed-origins` for production

3. **Use Managed Identity** for database connections (advanced):
   ```bash
   az webapp identity assign \
     --name blood-backend-api \
     --resource-group blood-donor-rg
   ```

4. **Enable Application Insights** for monitoring:
   ```bash
   az monitor app-insights component create \
     --app blood-donor-insights \
     --location eastus \
     --resource-group blood-donor-rg
   ```

---

## 📝 Startup Command Explained

```bash
java -Dserver.port=$PORT -jar /home/site/wwwroot/*.jar
```

**Breakdown:**
- `java`: JVM command
- `-Dserver.port=$PORT`: Sets Spring Boot server port to Azure's PORT environment variable
- `-jar`: Indicates we're running a JAR file
- `/home/site/wwwroot/*.jar`: Path where Azure stores deployed files
- `*.jar`: Matches any JAR file (blood-donor-finder-0.0.1-SNAPSHOT.jar)

**Why this works:**
- Azure App Service sets `$PORT` environment variable
- Spring Boot reads `-Dserver.port` and overrides application.properties
- Application listens on Azure's assigned port (usually 80/443)

---

## ✅ Deployment Checklist

Before deploying, ensure:

- [ ] Azure App Service created (`blood-backend-api`)
- [ ] Java 17 runtime configured
- [ ] Startup command set correctly
- [ ] Publish profile added to GitHub secrets
- [ ] Environment variables configured
- [ ] CORS origins updated for production
- [ ] Database configured (if not using H2)
- [ ] Health check endpoint enabled
- [ ] HTTPS-only enabled
- [ ] Application logs enabled

---

## 🎯 Expected Deployment Flow

1. **Push to GitHub** → Triggers `azure-deploy.yml` workflow
2. **Build Job**:
   - Checks out code
   - Sets up Java 17
   - Runs `mvn clean package`
   - Uploads JAR artifact
3. **Deploy Job**:
   - Downloads JAR artifact
   - Deploys to Azure App Service using publish profile
   - Azure starts application with startup command
4. **Result**: Application accessible at `https://blood-backend-api.azurewebsites.net`

---

## 📞 Support & Resources

- **Azure App Service Docs**: https://docs.microsoft.com/azure/app-service/
- **Spring Boot on Azure**: https://docs.microsoft.com/java/azure/spring-framework/
- **GitHub Actions for Azure**: https://github.com/Azure/actions

---

**Last Updated**: March 12, 2026  
**Status**: Production Ready ✅
