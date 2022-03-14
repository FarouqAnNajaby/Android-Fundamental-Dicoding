package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val list_user: List<Users>
)

data class Users(

	@field:SerializedName("gists_url")
	val gistsUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("starred_url")
	val starredUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("subscriptions_url")
	val subscriptionsUrl: String,

	@field:SerializedName("score")
	val score: Double,

	@field:SerializedName("received_events_url")
	val receivedEventsUrl: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("events_url")
	val eventsUrl: String,

	@field:SerializedName("html_url")
	val htmlUrl: String,

	@field:SerializedName("site_admin")
	val siteAdmin: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("node_id")
	val nodeId: String,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String
)

data class UserDetail(
	@SerializedName("login")
	val login: String,
	@SerializedName("id")
	val id: Int,
	@SerializedName("node_id")
	val node_id: String,
	@SerializedName("avatar_url")
	val avatar_url: String,
	@SerializedName("gravatar_id")
	val gravatar_id: String,
	@SerializedName("url")
	val url: String,
	@SerializedName("html_url")
	val html_url: String,
	@SerializedName("followers_url")
	val followers_url: String,
	@SerializedName("following_url")
	val following_url: String,
	@SerializedName("gists_url")
	val gists_url: String,
	@SerializedName("starred_url")
	val starred_url: String,
	@SerializedName("subscriptions_url")
	val subscriptions_url: String,
	@SerializedName("organizations_url")
	val organizations_url: String,
	@SerializedName("repos_url")
	val repos_url: String,
	@SerializedName("events_url")
	val events_url: String,
	@SerializedName("received_events_url")
	val received_events_url: String,
	@SerializedName("type")
	val type: String,
	@SerializedName("site_admin")
	val site_admin: Boolean,
	@SerializedName("name")
	val name: String? = null,
	@SerializedName("company")
	val company: String? = null,
	@SerializedName("blog")
	val blog: String,
	@SerializedName("location")
	val location: String? = null,
	@SerializedName("public_repos")
	val public_repos: Int,
	@SerializedName("public_gists")
	val public_gists: Int,
	@SerializedName("followers")
	val followers: Int,
	@SerializedName("following")
	val following: Int,
	@SerializedName("created_at")
	val created_at: String,
	@SerializedName("updated_at")
	val updated_at: String
)
