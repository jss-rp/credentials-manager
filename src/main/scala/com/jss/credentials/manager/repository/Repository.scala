package com.jss.credentials.manager.repository
import scala.collection.mutable

trait Repository[T]:
    def save(credential: T): T
    def findAll(): mutable.Set[T]