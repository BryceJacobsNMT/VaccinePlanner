#install.packages("deSolve", repos="http://cran.rstudio.com/") #if library not yet installed
#install.packages("plotly")
install.packages("xlsx")
#Loading libraries
library(ggplot2)
#library(plotly)
library(deSolve)
library(reshape2)

#getwd()
#setwd("/Users/Tianguan/Downloads") #or where input file is
args <- commandArgs(TRUE)
input <- read.table(args[1], header = TRUE)
days = 100

#create file for storing data
dir.create(file.path("plots"), showWarnings = FALSE)
dir.create(file.path("tables"), showWarnings = FALSE)

# Model inputs
for (x in 1:nrow(input)) {
  #N <- input[1, "Population"]
  #R <- input[x, "Vaccinated"] + input[x, "Recovered"]
  initial_state_values = c(S = input[x, "Population"] - (input[x, "Sick"] + 
                               input[x, "Recovered"] + input[x, "Vaccinated"]),
                           I = input[x, "Sick"],
                           R = input[x,"Recovered"] + input[x, "Vaccinated"])
  
  parameters = c(gamma=1/14,beta=0.4)
  
  # Time points
  
  time=seq(from=1,to=days,by=1)
  
  #SIR Model function
  sir_model3 <- function(time,state,parameters){
    with(as.list(c(state,parameters)),{
      N=S+I+R
      lambda=beta*(I/N)
      dS=-lambda*S
      dI=lambda*S-gamma*I
      dR=gamma*I
      
      return(list(c(dS,dI,dR)))
    }
    )
  }
  
  # Solving the differential equations:
  output<-as.data.frame(ode(y=initial_state_values,func = sir_model3,parms=parameters,times = time))
  
  out_long=melt(output,id="time")
  
  p <- ggplot(data = out_long,         
         aes(x = time, y = value, colour = variable, group = variable)) +  
    geom_line() +                                                          
    xlab("Time (days)")+                          
    ylab("Population") + scale_color_discrete(name = "State")
  #print(p)
  #png(filename="%dplot.png", x)
  #print(p)
  
  filename <- gsub(" ", "", paste("plot", x, ".png", collapse = NULL))
  
  ggsave(path = "plots", filename = filename)
  #save to txt
  write.table(output, gsub(" ", "", paste("tables/table", x, ".txt", collapse = NULL)), sep="\t")
  #save to csv
  write.csv(output, gsub(" ", "", paste("tables/table", x, ".csv", collapse = NULL)))
}
