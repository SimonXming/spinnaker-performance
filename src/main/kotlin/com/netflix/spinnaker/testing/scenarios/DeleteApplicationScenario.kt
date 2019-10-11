/*
 * Copyright 2017 Armory.
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

package com.netflix.spinnaker.testing.scenarios

import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.spinnaker.testing.ExecutionConfig
import com.netflix.spinnaker.testing.ScenarioConfig
import com.netflix.spinnaker.testing.api.SpinnakerClient
import com.netflix.spinnaker.testing.scenarios.Scenario
import com.netflix.spinnaker.testing.scenarios.ScenarioActivity
import java.util.*

class DeleteApplicationScenario(val objectMapper: ObjectMapper,
                                val spinnakerClient: SpinnakerClient,
                                val scenarioConfig: ScenarioConfig) : Scenario {
  private val config = objectMapper.convertValue(scenarioConfig.config, DeleteApplicationScenarioConfig::class.java)
  private val executionConfig = objectMapper.convertValue(scenarioConfig.executionConfig, ExecutionConfig::class.java)

  val scenarioId = UUID.randomUUID().toString()
  override fun plan(): List<ScenarioActivity> {
    return (1..executionConfig.total).map {
      val job = config.toJob(it.toString())
      job.put("scenarioId", scenarioId)
      job.put("tickId", it)

      ScenarioActivity(
        it,
        job,
        scenarioConfig.name,
        config.application,
        "Delete Application (id: ${scenarioId.substring(scenarioId.length - 6)}, tick: ${it})"
      )
    }
  }

}

private data class DeleteApplicationScenarioConfig(val application: String,
                                                   val cloudProviders: String
){
  fun toJob(stack: String): MutableMap<String, Any> {
    val app = mutableMapOf(
        Pair("name", application),
        Pair("cloudProviders", cloudProviders)
    )

    val job = mutableMapOf(
      Pair("application", app),
      Pair("type", "deleteApplication")
    )

    return job;
  }
}
