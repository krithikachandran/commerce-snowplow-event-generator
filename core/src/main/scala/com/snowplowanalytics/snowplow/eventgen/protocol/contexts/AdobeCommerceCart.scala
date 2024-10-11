/*
 * Copyright (c) 2021-2022 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package com.snowplowanalytics.snowplow.eventgen.protocol.contexts

import com.snowplowanalytics.iglu.core.{SchemaKey, SchemaVer}
import com.snowplowanalytics.snowplow.eventgen.protocol.SelfDescribingJsonGen
import com.snowplowanalytics.snowplow.eventgen.protocol.implicits._
import io.circe.Json
import org.scalacheck.Gen

import java.time.Instant

object AdobeCommerceCart extends SelfDescribingJsonGen {

  override def schemaKey: SchemaKey = {
    SchemaKey("com.adobe.magento.entity", "shopping-cart", "jsonschema", SchemaVer.Full(3, 0, 0))
    //SchemaKey("com.snowplowanalytics.snowplow", "client_session", "jsonschema", SchemaVer.Full(1, 0, 2))
  }

  override def fieldGens(now: Instant): Map[String, Gen[Option[Json]]] =
    Map(
      "cartId"              -> Gen.uuid.required,
      "itemsCount"          -> Gen.choose(0, 5).required,
      "subtotalExcludingTax" -> Gen.choose(0, 1000).required,
      "items"               -> Gen.listOfN(5, for {
        offerPrice <- Gen.choose(0, 1000)
        basePrice  <- Gen.choose(0, 1000)
        qty        <- Gen.choose(1, 5)
        productName <- Gen.alphaStr
        cartItemId <- Gen.alphaStr
        productSku <- Gen.alphaStr
        mainImageUrl <- Gen.alphaStr
      } yield Json.obj(
        "offerPrice" -> Json.fromInt(offerPrice),
        "basePrice"  -> Json.fromInt(basePrice),
        "qty"        -> Json.fromInt(qty),
        "productName" -> Json.fromString(productName),
        "cartItemId" -> Json.fromString(cartItemId),
        "productSku" -> Json.fromString(productSku),
        "mainImageUrl" -> Json.fromString(mainImageUrl)
      )).required,
      "possibleOnepageCheckout" -> Gen.oneOf(true, false).required,
      "subtotalIncludingTax" -> Gen.choose(0, 1000).required,
      "giftMessageSelected" -> Gen.oneOf(true, false).required,
      "subtotalAmount" -> Gen.choose(0, 1000).required,
      "giftWrappingSelected" -> Gen.oneOf(true, false).required
    )

  //  override def fieldGens(now: Instant): Map[String, Gen[Option[Json]]] =
  //    Map(
  //      "userId"              -> Gen.uuid.required,
  //      "sessionId"           -> Gen.uuid.required,
  //      "sessionIndex"        -> Gen.choose(0, 2147483647).required,
  //      "eventIndex"          -> Gen.choose(0, 2147483647).optionalOrNull,
  //      "previousSessionId"   -> Gen.uuid.orNull,
  //      "storageMechanism"    -> Gen.oneOf("SQLITE", "COOKIE_1", "COOKIE_3", "LOCAL_STORAGE", "FLASH_LSO").required,
  //      "firstEventId"        -> Gen.uuid.optionalOrNull,
  //      "firstEventTimestamp" -> genInstant(now).map(_.toString).optionalOrNull
  //    )
}
