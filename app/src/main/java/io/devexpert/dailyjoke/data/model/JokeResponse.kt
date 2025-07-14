package io.devexpert.dailyjoke.data.model

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("setup")
    val setup: String,
    @SerializedName("delivery")
    val delivery: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("flags")
    val flags: Flags? = null,
    @SerializedName("lang")
    val lang: String? = null,
    @SerializedName("safe")
    val safe: Boolean? = null
)

data class Flags(
    @SerializedName("nsfw")
    val nsfw: Boolean = false,
    @SerializedName("religious")
    val religious: Boolean = false,
    @SerializedName("political")
    val political: Boolean = false,
    @SerializedName("racist")
    val racist: Boolean = false,
    @SerializedName("sexist")
    val sexist: Boolean = false,
    @SerializedName("explicit")
    val explicit: Boolean = false
)