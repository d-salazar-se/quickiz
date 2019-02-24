import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import InputAdornment from '@material-ui/core/InputAdornment';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import AddIcon from '@material-ui/icons/Add';
import DeleteForeverIcon from '@material-ui/icons/DeleteForever';
import Grid from '@material-ui/core/Grid';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';
import Select from '@material-ui/core/Select';
import FilledInput from '@material-ui/core/FilledInput';
import MenuItem from '@material-ui/core/MenuItem';

class QuizCreateComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      quiz: {
        name: '',
        duration: 0,
        slacktime: 0,
        questionsStructures: []
      },
      pools: [],
      errors: {
        name: '',
        duration: '',
        slacktime: '',
        questionsStructures: ''
      },
      isLoading: true
    };

    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleChangeScore = this.handleChangeScore.bind(this);
    this.handleChangePool = this.handleChangePool.bind(this);
    this.addQuestion = this.addQuestion.bind(this);
    this.removeQuestion = this.removeQuestion.bind(this);
  }

  componentDidMount() {
    this.getPools();
  }

  getPools() {
    axios.get(process.env.REACT_APP_API_URL + '/pools')
      .then((result) => {
        this.setState({
          pools: result.data,
          isLoading: false
        });
        this.addQuestion();
      })
      .catch((err) => {
        this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al cargar los pozos!', { variant: 'error' });
      });
    }

  validate(event) {
    const quiz = this.state.quiz;
    
    var outcome = true;
    var errors = {
      name: '',
      duration: '',
      slacktime: '',
      questionsStructures: ''
    };
    
    try {
      if (quiz.name.length === 0 || quiz.name.length > 256) {
        outcome = false;
        errors.name = 'El campo es requerido y su largo debe ser menor a 256 caracteres.';
      }

      if (isNaN(quiz.duration) || quiz.duration <= 0 || quiz.duration > 30) {
        outcome = false;
        errors.duration = 'El campo es requerido y debe ser un número entero entre 1 y 30.';
      }

      if (isNaN(quiz.slacktime) || quiz.slacktime <= 0 || quiz.slacktime > 10) {
        outcome = false;
        errors.slacktime = 'El campo es requerido y debe ser un número entero entre 1 y 10.';
      }

      var totalScore = 0;
      for (var i = quiz.questionsStructures.length - 1; i >= 0; i--) {
        totalScore += quiz.questionsStructures[i].score;
      }

      if (totalScore !== 6) {
        errors.questionsStructures = 'El puntaje total de las preguntas DEBE sumar 6.';
        outcome = false;
      }

    } catch (err) {
      this.props.enqueueSnackbar('Error al intentar guardar los datos!', {
        variant: 'error',
      });
    }

    event.preventDefault();
    event.stopPropagation();

    if ( ! outcome) {
      this.setState({ errors: errors });
      this.props.enqueueSnackbar('Debes completar todos los campos correctamente!', { variant: 'warning' });
    }

    return outcome;
  }

  handleSubmit(event) {
    if ( ! this.validate(event)) {
      return;
    }

    let quiz = this.state.quiz;
    let questions = quiz.questionsStructures;
    delete quiz.questionsStructures;
    axios.post(process.env.REACT_APP_API_URL + '/quizstructures', {
      "quiz": quiz, "questions": questions
    })
     .then(result => {
        this.props.history.push('/quizzes/');
        this.props.enqueueSnackbar('Quiz creado!', { variant: 'success' });
      })
      .catch(error => {
        this.props.enqueueSnackbar('Error al guardar los datos!', { variant: 'error' });
      });

    // event.preventDefault();
    // event.stopPropagation();
  }

  handleChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      quiz: {
        ...this.state.quiz,
        [name]: value,
      },
    });
  }

  handleChangeScore(event, index) {
    let questionsStructures = this.state.quiz.questionsStructures;
    let score = event.target.value;

    questionsStructures[index].score = score;

    this.setState({
      quiz: {
        ...this.state.quiz,
        questionsStructures: questionsStructures
      }
    }); 
  }

  handleChangePool(event, index) {
    let questionsStructures = this.state.quiz.questionsStructures;
    let poolId = event.target.value;

    questionsStructures[index].poolId = poolId;

    this.setState({
      quiz: {
        ...this.state.quiz,
        questionsStructures: questionsStructures
      }
    });
  }

  addQuestion() {
    let questionsStructures = this.state.quiz.questionsStructures;
    let pools = this.state.pools;

    questionsStructures.push({
      score: 1,
      poolId: pools[0].id
    });
    
    this.setState({
      quiz: {
        ...this.state.quiz,
        questionsStructures: questionsStructures
      }
    });
  }

  removeQuestion(index) {
    let questionsStructures = this.state.quiz.questionsStructures;
    questionsStructures = questionsStructures.filter((_, i) => i !== index);
    
    this.setState({
      quiz: {
        ...this.state.quiz,
        questionsStructures: questionsStructures
      }
    });
  }

  render() {  
    return (
      <div>
        { this.state.isLoading
          ? <LinearProgress className="progress"/>
          : <div>
          <Paper>
            <div className="formcrear">
              <Typography  variant="h4" gutterBottom>Crear Quiz</Typography>
            
              <form onSubmit={this.handleSubmit}>
                <Grid container direction="row" alignItems="center" spacing={24}>
                  <Grid item xs={12}>
                    <TextField
                              id="name"
                              name="name"
                              label="Nombre"
                              error={this.state.errors.name !== ''}
                              helperText={this.state.errors.name}
                              placeholder="Ingrese nombre del quiz"
                              onChange={this.handleChange}
                              fullWidth
                              margin="normal"
                              variant="filled"
                              InputLabelProps={{
                                shrink: true,
                              }}
                            />
                  </Grid>
                  
                  <Grid item xs={3}>
                    <TextField
                              id="duration"
                              name="duration"
                              label="Duración"
                              error={this.state.errors.duration !== ''}
                              helperText={this.state.errors.duration}
                              onChange={this.handleChange}
                              fullWidth
                              type="number"
                              InputLabelProps={{
                                shrink: true,
                                min:"5"
                              }}
                              InputProps={{
                                min: "5",
                                max: "30",
                                step: "1",
                                endAdornment: (
                                              <InputAdornment variant="filled" position="end">
                                                Minutos
                                              </InputAdornment>
                                            ),
                              }}
                              placeholder="Duración máxima"
                              margin="normal"
                              variant="filled"
                            />
                  </Grid>

                  <Grid item xs={3}>
                    <TextField
                              id="slacktime"
                              name="slacktime"
                              label="Holgura"
                              placeholder="Holgura"
                              error={this.state.errors.slacktime !== ''}
                              helperText={this.state.errors.slacktime}
                              onChange={this.handleChange}
                              fullWidth
                              type="number"
                              InputLabelProps={{
                                shrink: true,
                                min:"1"
                              }}
                              InputProps={{
                                min: "1",
                                max: "5",
                                step: "1",
                                endAdornment: (
                                              <InputAdornment variant="filled" position="end">
                                                Minutos
                                              </InputAdornment>
                                            ),
                              }}
                              margin="normal"
                              variant="filled"
                            />
                  </Grid>

                  <Grid item xs={4}>
                    <Button variant="contained" color="primary" onClick={this.addQuestion}>
                      <AddIcon /> Agregar Pregunta
                    </Button>
                  </Grid>
                
                  { this.state.quiz.questionsStructures.map((question, index) => {
                    return (
                      <Grid item xs={12} key={index}>
                        <Grid container direction="row" alignItems="center" spacing={24}>
                          <Grid item xs={2}>
                            <FormControl variant="filled" fullWidth>
                              <InputLabel htmlFor="question">Puntaje</InputLabel>
                              <Select
                                value={question.score}
                                onChange={(event) => this.handleChangeScore(event, index)}
                                input={<FilledInput name="score[]" />}
                              >
                                <MenuItem value={1}>1</MenuItem>
                                <MenuItem value={2}>2</MenuItem>
                                <MenuItem value={3}>3</MenuItem>
                                <MenuItem value={4}>4</MenuItem>
                                <MenuItem value={5}>5</MenuItem>
                                <MenuItem value={6}>6</MenuItem>
                              </Select>
                            </FormControl>
                          </Grid>
                          
                          <Grid item xs={9}>
                            <FormControl variant="filled" fullWidth>
                              <InputLabel htmlFor="question">Pozo</InputLabel>
                              <Select
                                value={this.state.quiz.questionsStructures[index].poolId}
                                onChange={(event) => this.handleChangePool(event, index)}
                                input={<FilledInput name="pool[]" />}
                              >
                                {
                                  this.state.pools.map(pool => {
                                    return (
                                      <MenuItem key={pool.id} value={pool.id}>{pool.name}</MenuItem>
                                    );
                                  })
                                }
                              </Select>
                            </FormControl>
                          </Grid>

                          <Grid item xs={1}>
                            <Button variant="contained" color="primary" onClick={(event) => this.removeQuestion(index)}>
                              <DeleteForeverIcon />
                            </Button>
                          </Grid>
                        </Grid>
                      </Grid>
                    );
                  })}

                  <Grid item xs={12}>
                    <Typography color="error">{ this.state.errors.questionsStructures }</Typography>
                  </Grid>
                
                  <Grid item xs={12}>
                    <div className="btn-group">
                      <Button component={Link} to="/quizzes">
                        Cancelar
                      </Button>

                      <Button variant="contained" color="primary" className="btn-primary" type="submit">
                        Crear
                      </Button>
                    </div>
                  </Grid>
                </Grid>
              </form>
            </div>
          </Paper>
          </div>
        }
      </div>
    );
  }
}

export default withSnackbar(QuizCreateComponent);
