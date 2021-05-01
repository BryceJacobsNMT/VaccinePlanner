#usage: Rscript SIR.R <filename> <days>
#only required input is filename, other values are set otherwise

#Loading libraries
if (!require("ggplot2")) install.packages("ggplot2", repos = "http://cran.us.r-project.org"); library("ggplot2")
if (!require("deSolve")) install.packages("deSolve", repos = "http://cran.us.r-project.org"); library("deSolve")
if (!require("reshape2")) install.packages("reshape2", repos = "http://cran.us.r-project.org"); library("reshape2")
#if (!require("reshape2")) install.packages("plotly", repos = "http://cran.us.r-project.org"); library("plotly")

args <- commandArgs(TRUE)
input <- read.table("input.txt", header = TRUE)

#check if other inputs are available
if(length(args[2]) > 0 & is.numeric(args[2])) {
  days = args[2]
} else {
  days = 100
}

if(length(args[3]) > 0 & is.numeric(args[3])) {
  days = args[2]
}

#create file for storing data
dir.create(file.path("graphs"), showWarnings = FALSE)
dir.create(file.path("tables"), showWarnings = FALSE)

#initialize vectors to store distribution statistics
r_e <- vector()
immunity_percent <- vector()
immunity_population <- vector()

# Model inputs
for (x in 1:nrow(input)) {
  #N <- input[1, "Population"]
  #R <- input[x, "Vaccinated"] + input[x, "Recovered"]
  initial_state_values = c(Susceptible = input[x, "Population"] - (input[x, "Sick"] + 
                                                           input[x, "Recovered"] + input[x, "Vaccinated"]),
                           Infected = input[x, "Sick"],
                           Recovered = input[x,"Recovered"] + input[x, "Vaccinated"])
  
  s = input[x, "Population"] - (input[x, "Sick"] + 
                                  input[x, "Recovered"] + input[x, "Vaccinated"])
  n = input[x, "Population"]
  bigS <- s/n
  r0 <- NULL
  re <- NULL
  
  parameters = c(gamma = 1/14, beta = 5/28)
  
  # Time points
  
  time = seq(from=1,to=days,by=1)
  
  #SIR Model function
  sir_model3 <- function(time, state, parameters){
    with(as.list(c(state, parameters)),{
      N = Susceptible + Infected + Recovered
      lambda = beta * (Infected / N)
      dS = -lambda * Susceptible
      dI = lambda * Susceptible - gamma * Infected
      dR = gamma * Infected
      
      assign("r0", beta/gamma, envir = globalenv())
      assign("re", r0 * bigS, envir = globalenv())
      
      
      return(list(c(dS, dI, dR)))
    }
    )
  }
  
  # Solving the differential equations:
  output<-as.data.frame(ode(y=initial_state_values,func = sir_model3,parms=parameters,times = time))
  
  #place distribution values into vectors
  immunity_threshold = 1 - 1/re
  immunity = immunity_threshold*input[x, "Population"]
  
  r_e <- append(r_e, re)
  immunity_percent <- append(immunity_percent, immunity_threshold*100)
  immunity_population <- append(immunity_population, immunity)
  
  out_long=melt(output,id="time")
  
  p <- ggplot(data = out_long,         
              aes(x = time, y = value, colour = variable, group = variable)) +  
    geom_line() + 
    #geom_line(aes(y=immunity, colour = "immunity")) +                                                         
    xlab("Time (days)") +                          
    ylab("Population") + scale_color_discrete(name = "State")
  
  #print(p)
  #png(filename="%dplot.png", x)
  #print(p)
  
  filename <- gsub(" ", "", paste("graph", x, ".png", collapse = NULL))
  
  ggsave(path = "graphs", filename = filename)
  #save to txt
  write.table(output, gsub(" ", "", paste("tables/table", x, ".txt", collapse = NULL)), sep="\t")
  #save to csv
  write.csv(output, gsub(" ", "", paste("tables/table", x, ".csv", collapse = NULL)))
}

distribution <- data.frame(r_e,
                           immunity_percent,
                           immunity_population)

write.table(output, gsub(" ", "", paste("tables/distribution", ".txt", collapse = NULL)), sep="\t")
#save to csv
write.csv(output, gsub(" ", "", paste("tables/distribution", ".csv", collapse = NULL)))
