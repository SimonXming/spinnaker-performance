/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.netflix.spinnaker.testing.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Headers

const val authCookie = "Cookie: SESSION=OTZiMGM4NDktYWZkZS00N2I5LWFhNDItZGMyMjFiMmQwYjVj"

interface SpinnakerClient {
  @GET("/health")
  fun health(): Call<Map<String, Any>>

  @Headers(authCookie)
  @POST("/tasks")
  fun submitTask(@Body task: Task): Call<SubmittedTask>

  @Headers(authCookie)
  @POST("/pipelines")
  fun submitPipeline(@Body pipeline: Pipeline): Call<Void>

  @Headers(authCookie)
  @GET("/applications/{application}/serverGroups")
  fun getServerGroupsForApplication(@Path("application") application: String): Call<List<ServerGroup>>

  @Headers(authCookie)
  @GET("/applications/{application}/serverGroupManagers")
  fun getServerGroupManagersForApplication(@Path("application") application: String): Call<List<ServerGroupManager>>

  @Headers(authCookie)
  @GET("/applications/{application}/tasks")
  fun getTasksForApplication(@Path("application") application: String): Call<List<TaskResult>>

  @Headers(authCookie)
  @GET("/tasks/{taskId}")
  fun getTask(@Path("taskId") taskId: String): Call<TaskResult>
}

data class ServerGroup(val name: String,
                       val account: String,
                       val region: String,
                       val cloudProvider: String,
                       val createdTime: Long)

data class ServerGroupManager(val name: String,
                              val account: String,
                              val region: String,
                              val cloudProvider: String)

data class Task(val job: List<Map<String, Any>>, val application: String, val description: String)

data class SubmittedTask(val ref: String)

data class TaskResult(val id: String,
                      val status: String,
                      val variables: List<TaskVariable>,
                      val buildTime: Long,
                      val startTime: Long,
                      val endTime: Long) {
  fun getVariable(key: String): TaskVariable? = variables.find { it.key == key }
  fun getDuration() = (endTime - startTime) / 1000
  fun getLag() = (startTime - buildTime) / 1000
}

data class TaskVariable(val key: String, val value: Any?)

data class PipelineStage(val name: String, val type: String, val waitTime: Long)

data class Pipeline(val name: String,
                    val application: String,
                    val limitConcurrent: Boolean,
                    val keepWaitingPipelines: Boolean,
                    val stages: List<PipelineStage>)

// TODO
data class SubmittedPipeline(val ref: String)
