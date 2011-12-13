dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    dialect = org.hibernate.dialect.MySQL5InnoDBDialect // must be set for transactions to work!
    dbCreate = "create-drop"
    url = "jdbc:mysql://localhost:3306/app?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8"
    username = "root"
    password = "igdefault"

}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
