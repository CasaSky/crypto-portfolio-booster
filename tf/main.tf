terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.1.7"
}

resource "aws_sns_topic" "crypto_purchase" {
  name = "crypto-purchase"
}