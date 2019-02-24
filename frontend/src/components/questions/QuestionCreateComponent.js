import React, { Component } from 'react';

import { Link } from 'react-router-dom';
import axios from 'axios';
import { withSnackbar } from 'notistack';
import LinearProgress from '@material-ui/core/LinearProgress';

import TextField from '@material-ui/core/TextField';
import FormHelperText from '@material-ui/core/FormHelperText';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import FilledInput from '@material-ui/core/FilledInput';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import Grid from '@material-ui/core/Grid';
import Tooltip from '@material-ui/core/Tooltip';

const variables = `
Ejemplo: "a":[1,2,3,4] donde a, es la variable y [1,2,3,4] son los valores que puede tomar.
Ejemplo: "var":["a","hola","abrir"],"list":[[],[1,2],[1,2,3]]
`;
const enunciado = `
Ingrese el enunciado que tendrá su código.
`;
const codigo = `
Ingrese el código Python que tendrá su pregunta.
`;
const pozo = `
Seleccione el pozo al que pertenecerá su pregunta.
`;
class QuestionCreateComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,

      question: {
        poolId: '',
        title: '',
        code: '',
        variables: '',
      },

      variables:{},

      pools: [],

      errors: {
        title: '',
        variables: '',
        code: '',
      },
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  componentDidMount() {
    this.getPools();
  }

  getPools() {
    axios
      .get(process.env.REACT_APP_API_URL + '/pools')
      .then(result => {
          question.poolId = result.data[0];
          this.setState({
            isLoading: false,
            pools: result.data,
            question: question
          });
        }
      )
      .catch(error =>
        this.setState({
          isLoading: false,
          pools: [],
        })
      );
  }

  handleChange(event) {
    const target = event.target;
    const value = target.type === 'checkbox' ? target.checked : target.value;
    const name = target.name;

    this.setState({
      question: {
        ...this.state.question,
        [name]: value,
      },

      // errors: {
      //   ...this.state.errors,
      //   [name]: ''
      // }
    });
  }

  convertirJSON(variables){
    variables = "{" + variables + "}";
    try {
      variables = variables.replace(/'/g,"\"");
      variables = JSON.parse(variables);

      this.setState(variables: variables);
      console.log();
    } catch (ex) {
      console.error(ex);
    }
  }

  validateSubmit(event) {
    const question = this.state.question;

    var outcome = true;

    try {
      if (question.title.length === 0 || question.title.length > 256) {
        this.setState({
          errors: {
            title:
              'El campo enunciado es requerido y su largo debe ser menor a 256 caracteres.',
          },
        });
        outcome = false;
      }

      if (question.code.length === 0) {
        this.setState({
          errors: {
            code:
              'El campo código es requerido y su largo debe ser menor a 256 caracteres.',
          },
        });
        outcome = false;
      }
    } catch (err) {
      this.props.enqueueSnackbar('Error al guardar la pregunta!', {
        variant: 'error',
      });
    }

    event.preventDefault();
    event.stopPropagation();
    return outcome;
  }

  handleSubmit(event) {
    if (!this.validateSubmit(event)) {
      event.preventDefault();
      return;
    }

    this.setState({
      isLoading: true
    });
    this.convertirJSON(this.state.question.variables);

    let question = this.state.question;
    question.variables = question.variables.replace(/:/g,"=");
    this.setState({
      question: question
    });
    console.log(this.state);
    console.log({variables: this.state.variables, question:this.state.question});

    axios
      .post(process.env.REACT_APP_API_URL + '/questions', {variables: this.state.variables, question:this.state.question})
      .then(result => {
        this.setState({
          isLoading: false,
          question: result.data,
        });
       
        this.props.history.push('/questions');
        this.props.enqueueSnackbar('Pregunta creada!', { variant: 'success' });
      })
      .catch(error => {
        this.setState({ isLoading: false });
        this.props.enqueueSnackbar('Error al crear la pregunta!', {
          variant: 'error',
        });
      });

    event.preventDefault();
  }

  render() {
    return (
      <div>
        { this.state.isLoading
          ? <LinearProgress className="progress" />
          : <div>
            <Typography variant="h4" gutterBottom>
              Agregar Pregunta al Pozo
            </Typography>

            <Grid container spacing={24}>
              <Grid item xs={12}>
                <div className="panelcrear">
                  <div className="formcrear">
                    <form onSubmit={this.handleSubmit}>
                      <Tooltip title={pozo} placement="right">
                        <FormControl variant="filled" margin="normal" fullWidth>
                          <InputLabel htmlFor="poolId">Pozo</InputLabel>
                          <Select
                            value={this.state.question.poolId}
                            onChange={this.handleChange}
                            input={<FilledInput name="poolId" id="poolId" />}
                          >
                            {this.state.pools.map(pool => {
                              return (
                                <MenuItem key={pool.id} value={pool.id}>
                                  {pool.name}
                                </MenuItem>
                              );
                            })}
                            ;
                          </Select>
                        </FormControl>
                      </Tooltip>

                      <Tooltip title={enunciado} placement="right">
                        <TextField
                          error={this.state.errors.title !== ''}
                          id="title"
                          name="title"
                          label="Enunciado"
                          placeholder="Enunciado de la pregunta"
                          multiline
                          rows="5"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>
                      <FormHelperText>{this.state.errors.title}</FormHelperText>

                      <Tooltip title={variables} placement="right">
                        <TextField
                          error={this.state.errors.variables !== ''}
                          id="variables"
                          name="variables"
                          label="Ingrese las variables"
                          placeholder='Ejemplo: "a":1,"var":["a","hola","abrir"],"list":[[ ],[1,2],[1,2,3]]'
                          multiline
                          rows="3"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>
                      <FormHelperText>
                        {this.state.errors.variables}
                      </FormHelperText>

                      <Tooltip title={codigo} placement="right">
                        <TextField
                          error={this.state.errors.code !== ''}
                          id="code"
                          name="code"
                          label="Código"
                          placeholder="Ingrese código Python"
                          multiline
                          rows="10"
                          onChange={this.handleChange}
                          fullWidth
                          margin="normal"
                          variant="filled"
                          InputLabelProps={{
                            shrink: true,
                          }}
                        />
                      </Tooltip>
                      <FormHelperText>{this.state.errors.code}</FormHelperText>

                      <div className="btn-group">
                        <Button component={Link} to="/pools">
                          Cancelar
                        </Button>

                        <Button
                          variant="contained"
                          color="primary"
                          className="btn-primary"
                          type="submit"
                        >
                          Crear
                        </Button>
                      </div>
                    </form>
                  </div>
                </div>
              </Grid>
              {/*<Grid item xs={6}>
                  <Typography variant="h6" gutterBottom>Vista Previa</Typography>
                </Grid>*/}
            </Grid>
          </div>
        }
      </div>
    );
  }
}

export default withSnackbar(QuestionCreateComponent);
