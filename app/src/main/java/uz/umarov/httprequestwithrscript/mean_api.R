library(plumber)

calculate_mean <- function(numbers) {
  mean(numbers)
}

pr <- plumber::plumber()

pr$register("/calculate_mean", function(req, res) {
  body <- req$postBody
  numbers <- as.numeric(unlist(strsplit(body, ",")))

  result <- calculate_mean(numbers)

  res$status <- 200
  return(as.character(result))
})

pr$run(port=8000)
