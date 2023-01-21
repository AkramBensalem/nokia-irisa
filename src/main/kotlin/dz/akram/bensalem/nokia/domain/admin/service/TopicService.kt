package dz.akram.bensalem.nokia.domain.admin.service

import arrow.core.Either
import dz.akram.bensalem.nokia.domain.admin.entity.NameSpace
import dz.akram.bensalem.nokia.domain.admin.entity.Topic

interface TopicService {

    fun listTopics(namespace : NameSpace): List<Topic>
    fun createTopic(topic : Topic): Either<String, Boolean>
    fun deleteTopic(topic : Topic): Either<String, Boolean>
    fun checkTopicIfExists(topic : Topic): Either<String, Boolean>

}