package uz.usoft.test.data.model

data class MyResponse(
        val etag: String,
        val items: List<Item>,
        val kind: String,
        val nextPageToken: String,
        val pageInfo: PageInfo,
        val regionCode: String
)