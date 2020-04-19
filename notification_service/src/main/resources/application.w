server.port=0
spring.application.name=notification_service

eureka.instance.prefer-ip-address=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka

spring.mail.host=smtp.mail.ru
spring.mail.port=587
spring.mail.username=c32068@mail.ru
spring.mail.password=Inozemtsev1984

# Other properties
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

# TLS , port 587
spring.mail.properties.mail.smtp.starttls.enable=true


spring.data.mongodb.uri = mongodb+srv://Izum4ik:Izum170809@cluster0-r3vd9.mongodb.net/IL-notifications?retryWrites=true&w=majority
